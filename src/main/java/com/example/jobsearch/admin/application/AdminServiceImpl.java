package com.example.jobsearch.admin.application;

import com.example.jobsearch.admin.web.AdminUserResponse;
import com.example.jobsearch.auth.persistence.RefreshTokenRepository;
import com.example.jobsearch.exceptions.ResourceNotFoundException;
import com.example.jobsearch.jobapplication.application.JobApplicationService;
import com.example.jobsearch.jobapplication.domain.JobApplicationStatus;
import com.example.jobsearch.jobapplication.web.JobApplicationResponse;
import com.example.jobsearch.jobapplication.web.PageResponse;
import com.example.jobsearch.user.domain.RoleName;
import com.example.jobsearch.user.domain.User;
import com.example.jobsearch.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JobApplicationService jobApplicationService;
    private final Clock authClock;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<AdminUserResponse> findUsers(String search, RoleName role, Boolean enabled, Pageable pageable) {
        String normalized = search == null ? "" : search.trim();
        return PageResponse.from(userRepository.searchForAdmin(normalized, role, enabled, pageable)
                .map(this::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public AdminUserResponse findUser(long id) {
        return toResponse(requireUser(id));
    }

    @Override
    @Transactional
    public AdminUserResponse setUserEnabled(long adminId, long userId, boolean enabled) {
        User user = requireUser(userId);
        if (adminId == userId && !enabled) {
            throw new AdminConflictException("Administrator cannot disable their own account");
        }
        user.setEnabled(enabled);
        if (!enabled) {
            refreshTokenRepository.revokeAllActiveByUserId(userId, authClock.instant());
        }
        return toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<JobApplicationResponse> findJobApplications(
            Long vacancyId, JobApplicationStatus status, Pageable pageable) {
        return jobApplicationService.findAllForAdmin(vacancyId, status, pageable);
    }

    private User requireUser(long id) {
        return userRepository.findDetailedById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User " + id + " was not found"));
    }

    private AdminUserResponse toResponse(User user) {
        return new AdminUserResponse(user.getId(), user.getName(), user.getSurname(), user.getAge(), user.getEmail(),
                user.getPhoneNumber(), user.getAddress(), user.getCompanyName(), user.getRole().getName(),
                user.isEnabled());
    }
}
