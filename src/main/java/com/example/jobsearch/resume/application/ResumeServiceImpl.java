package com.example.jobsearch.resume.application;

import com.example.jobsearch.auth.application.AuthForbiddenException;
import com.example.jobsearch.auth.application.AuthUnauthorizedException;
import com.example.jobsearch.category.persistence.CategoryRepository;
import com.example.jobsearch.exceptions.ResourceNotFoundException;
import com.example.jobsearch.resume.domain.*;
import com.example.jobsearch.resume.persistence.ResumeRepository;
import com.example.jobsearch.resume.web.*;
import com.example.jobsearch.user.domain.User;
import com.example.jobsearch.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ResumeMapper mapper;
    private final Clock authClock;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ResumeResponse> findAll(String search, Long categoryId, Pageable pageable, String role) {
        String normalized = search == null ? "" : search.trim();
        boolean activeOnly = !"ADMIN".equals(role);
        Page<Resume> page = resumeRepository.search(normalized, categoryId, activeOnly, pageable);
        if (page.isEmpty()) return PageResponse.from(Page.empty(pageable));
        List<Long> ids = page.getContent().stream().map(Resume::getId).toList();
        Map<Long, Resume> detailed = resumeRepository.findDetailedByIdIn(ids).stream()
                .collect(Collectors.toMap(Resume::getId, Function.identity()));
        return PageResponse.from(page.map(item -> mapper.toResponse(detailed.get(item.getId()))));
    }

    @Override
    @Transactional(readOnly = true)
    public ResumeResponse findById(long id, long currentUserId, String role) {
        Resume resume = detailed(id);
        if (!resume.isActive() && !isAdmin(role) && !owns(resume, currentUserId)) throw new ResumeAccessDeniedException();
        return mapper.toResponse(resume);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumeResponse> findMine(long currentUserId) {
        currentUser(currentUserId);
        return resumeRepository.findByApplicantIdOrderByIdDesc(currentUserId).stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional
    public ResumeResponse create(long currentUserId, ResumeRequest request) {
        User applicant = currentUser(currentUserId);
        Resume resume = new Resume();
        resume.setApplicant(applicant);
        resume.setCreatedDate(LocalDateTime.now(authClock));
        apply(resume, request);
        return mapper.toResponse(resumeRepository.save(resume));
    }

    @Override
    @Transactional
    public ResumeResponse update(long id, long currentUserId, String role, ResumeRequest request) {
        currentUser(currentUserId);
        Resume resume = detailed(id);
        if (!isAdmin(role) && !owns(resume, currentUserId)) throw new ResumeAccessDeniedException();
        apply(resume, request);
        return mapper.toResponse(resume);
    }

    @Override
    @Transactional
    public void delete(long id, long currentUserId, String role) {
        currentUser(currentUserId);
        Resume resume = detailed(id);
        if (!isAdmin(role) && !owns(resume, currentUserId)) throw new ResumeAccessDeniedException();
        if (resumeRepository.hasApplications(id)) throw new ResumeInUseException();
        resumeRepository.delete(resume);
    }

    private void apply(Resume resume, ResumeRequest request) {
        validateDates(request);
        resume.setName(request.name());
        resume.setSalary(request.salary());
        resume.setActive(request.active());
        resume.setCategory(categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category " + request.categoryId() + " was not found")));
        resume.setUpdateDate(LocalDateTime.now(authClock));
        resume.getEducationInfo().clear();
        request.education().forEach(dto -> {
            EducationInfo item = new EducationInfo(); item.setResume(resume); item.setInstitution(dto.institution());
            item.setProgram(dto.program()); item.setDegree(dto.degree()); item.setStartDate(dto.startDate());
            item.setEndDate(dto.endDate()); resume.getEducationInfo().add(item);
        });
        resume.getWorkExperienceInfo().clear();
        request.workExperience().forEach(dto -> {
            WorkExperienceInfo item = new WorkExperienceInfo(); item.setResume(resume); item.setStartDate(dto.startDate());
            item.setEndDate(dto.endDate()); item.setCompanyName(dto.companyName()); item.setPosition(dto.position());
            item.setResponsibilities(dto.responsibilities()); resume.getWorkExperienceInfo().add(item);
        });
        if (request.socialLinks() == null) {
            resume.setSocialLinks(null);
        } else {
            SocialLinks links = resume.getSocialLinks() == null ? new SocialLinks() : resume.getSocialLinks();
            links.setResume(resume); links.setTelegram(request.socialLinks().telegram());
            links.setFacebook(request.socialLinks().facebook()); links.setLinkedin(request.socialLinks().linkedin());
            resume.setSocialLinks(links);
        }
    }

    private void validateDates(ResumeRequest request) {
        request.education().forEach(e -> dateOrder(e.startDate(), e.endDate()));
        request.workExperience().forEach(w -> dateOrder(w.startDate(), w.endDate()));
    }

    private void dateOrder(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) throw new ResumeValidationException("Start date must not be after end date");
    }

    private Resume detailed(long id) {
        return resumeRepository.findDetailedById(id).orElseThrow(() -> new ResumeNotFoundException(id));
    }

    private User currentUser(long id) {
        User user = userRepository.findById(id).orElseThrow(AuthUnauthorizedException::new);
        if (!user.isEnabled()) throw new AuthForbiddenException();
        return user;
    }

    private boolean owns(Resume resume, long id) { return Objects.equals(resume.getApplicant().getId(), id); }
    private boolean isAdmin(String role) { return "ADMIN".equals(role); }
}
