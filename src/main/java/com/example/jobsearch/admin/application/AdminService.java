package com.example.jobsearch.admin.application;

import com.example.jobsearch.admin.web.AdminUserResponse;
import com.example.jobsearch.jobapplication.domain.JobApplicationStatus;
import com.example.jobsearch.jobapplication.web.JobApplicationResponse;
import com.example.jobsearch.jobapplication.web.PageResponse;
import com.example.jobsearch.user.domain.RoleName;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    PageResponse<AdminUserResponse> findUsers(String search, RoleName role, Boolean enabled, Pageable pageable);
    AdminUserResponse findUser(long id);
    AdminUserResponse setUserEnabled(long adminId, long userId, boolean enabled);
    PageResponse<JobApplicationResponse> findJobApplications(Long vacancyId, JobApplicationStatus status,
                                                             Pageable pageable);
}
