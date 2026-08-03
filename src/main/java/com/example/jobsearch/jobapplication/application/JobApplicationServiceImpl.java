package com.example.jobsearch.jobapplication.application;

import com.example.jobsearch.auth.application.AuthForbiddenException;
import com.example.jobsearch.auth.application.AuthUnauthorizedException;
import com.example.jobsearch.jobapplication.domain.*;
import com.example.jobsearch.jobapplication.persistence.JobApplicationRepository;
import com.example.jobsearch.jobapplication.web.*;
import com.example.jobsearch.resume.application.ResumeNotFoundException;
import com.example.jobsearch.resume.domain.Resume;
import com.example.jobsearch.resume.persistence.ResumeRepository;
import com.example.jobsearch.user.domain.User;
import com.example.jobsearch.user.persistence.UserRepository;
import com.example.jobsearch.vacancy.domain.Vacancy;
import com.example.jobsearch.vacancy.persistence.VacancyRepository;
import com.example.jobsearch.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {
    private final JobApplicationRepository repository;
    private final VacancyRepository vacancyRepository;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final JobApplicationMapper mapper;
    private final Clock authClock;

    @Override
    @Transactional
    public JobApplicationResponse create(long currentUserId, JobApplicationRequest request) {
        currentUser(currentUserId);
        Vacancy vacancy = vacancyRepository.findById(request.vacancyId())
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy " + request.vacancyId() + " was not found"));
        Resume resume = resumeRepository.findDetailedById(request.resumeId())
                .orElseThrow(() -> new ResumeNotFoundException(request.resumeId()));
        if (!Objects.equals(resume.getApplicant().getId(), currentUserId)) throw new JobApplicationAccessDeniedException();
        if (!vacancy.isActive()) throw new JobApplicationConflictException("Applications are closed for this vacancy");
        if (!resume.isActive()) throw new JobApplicationConflictException("Inactive resume cannot be used");
        if (repository.existsByVacancyIdAndResumeId(vacancy.getId(), resume.getId())) {
            throw new JobApplicationConflictException("This resume has already been submitted for this vacancy");
        }
        LocalDateTime now = LocalDateTime.now(authClock);
        JobApplication application = new JobApplication();
        application.setVacancy(vacancy);
        application.setResume(resume);
        application.setStatus(JobApplicationStatus.CREATED);
        application.setCreatedAt(now);
        application.setUpdatedAt(now);
        try {
            return mapper.toResponse(repository.saveAndFlush(application));
        } catch (DataIntegrityViolationException exception) {
            throw new JobApplicationConflictException("This resume has already been submitted for this vacancy");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<JobApplicationResponse> findMine(long currentUserId, Pageable pageable) {
        currentUser(currentUserId);
        return map(repository.findByResumeApplicantIdOrderByCreatedAtDesc(currentUserId, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<JobApplicationResponse> findForVacancy(
            long vacancyId, long currentUserId, String role, Pageable pageable) {
        currentUser(currentUserId);
        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy " + vacancyId + " was not found"));
        if (!isAdmin(role) && (!isEmployer(role) || !Objects.equals(vacancy.getEmployer().getId(), currentUserId))) {
            throw new JobApplicationAccessDeniedException();
        }
        return map(repository.findByVacancyIdOrderByCreatedAtDesc(vacancyId, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<JobApplicationResponse> findAllForAdmin(
            Long vacancyId, JobApplicationStatus status, Pageable pageable) {
        return map(repository.searchForAdmin(vacancyId, status, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public JobApplicationResponse findById(long id, long currentUserId, String role) {
        currentUser(currentUserId);
        JobApplication application = detailed(id);
        requireVisible(application, currentUserId, role);
        return mapper.toResponse(application);
    }

    @Override
    @Transactional
    public JobApplicationResponse updateStatus(long id, long currentUserId, String role, JobApplicationStatus target) {
        currentUser(currentUserId);
        JobApplication application = detailed(id);
        if (isAdmin(role)) {
            requireTransition(application.getStatus(), target);
        } else if (isEmployer(role) && Objects.equals(application.getVacancy().getEmployer().getId(), currentUserId)) {
            if (target == JobApplicationStatus.WITHDRAWN) throw new JobApplicationConflictException("Employer cannot withdraw an application");
            requireTransition(application.getStatus(), target);
        } else if (isApplicant(role) && Objects.equals(application.getResume().getApplicant().getId(), currentUserId)) {
            if (target != JobApplicationStatus.WITHDRAWN) throw new JobApplicationAccessDeniedException();
            requireTransition(application.getStatus(), target);
        } else {
            throw new JobApplicationAccessDeniedException();
        }
        application.setStatus(target);
        application.setUpdatedAt(LocalDateTime.now(authClock));
        return mapper.toResponse(application);
    }

    @Override
    @Transactional
    public void deleteOrWithdraw(long id, long currentUserId, String role) {
        currentUser(currentUserId);
        JobApplication application = detailed(id);
        if (isAdmin(role)) {
            repository.delete(application);
            return;
        }
        if (!isApplicant(role) || !Objects.equals(application.getResume().getApplicant().getId(), currentUserId)) {
            throw new JobApplicationAccessDeniedException();
        }
        requireTransition(application.getStatus(), JobApplicationStatus.WITHDRAWN);
        application.setStatus(JobApplicationStatus.WITHDRAWN);
        application.setUpdatedAt(LocalDateTime.now(authClock));
    }

    private void requireTransition(JobApplicationStatus current, JobApplicationStatus target) {
        if (current == target) throw new JobApplicationConflictException("Application already has this status");
        if (EnumSet.of(JobApplicationStatus.ACCEPTED, JobApplicationStatus.REJECTED,
                JobApplicationStatus.WITHDRAWN).contains(current)) {
            throw new JobApplicationConflictException("Final application status cannot be changed");
        }
        boolean allowed = switch (current) {
            case CREATED -> EnumSet.of(JobApplicationStatus.REVIEWED, JobApplicationStatus.ACCEPTED,
                    JobApplicationStatus.REJECTED, JobApplicationStatus.WITHDRAWN).contains(target);
            case REVIEWED -> EnumSet.of(JobApplicationStatus.ACCEPTED, JobApplicationStatus.REJECTED,
                    JobApplicationStatus.WITHDRAWN).contains(target);
            default -> false;
        };
        if (!allowed) throw new JobApplicationConflictException("Invalid application status transition");
    }

    private void requireVisible(JobApplication application, long userId, String role) {
        if (isAdmin(role)) return;
        if (isApplicant(role) && Objects.equals(application.getResume().getApplicant().getId(), userId)) return;
        if (isEmployer(role) && Objects.equals(application.getVacancy().getEmployer().getId(), userId)) return;
        throw new JobApplicationAccessDeniedException();
    }

    private PageResponse<JobApplicationResponse> map(Page<JobApplication> page) {
        return PageResponse.from(page.map(mapper::toResponse));
    }

    private JobApplication detailed(long id) {
        return repository.findDetailedById(id).orElseThrow(() -> new JobApplicationNotFoundException(id));
    }

    private User currentUser(long id) {
        User user = userRepository.findById(id).orElseThrow(AuthUnauthorizedException::new);
        if (!user.isEnabled()) throw new AuthForbiddenException();
        return user;
    }

    private boolean isApplicant(String role) { return "APPLICANT".equals(role); }
    private boolean isEmployer(String role) { return "EMPLOYER".equals(role); }
    private boolean isAdmin(String role) { return "ADMIN".equals(role); }
}
