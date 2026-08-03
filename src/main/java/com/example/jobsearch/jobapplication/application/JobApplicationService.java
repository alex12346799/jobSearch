package com.example.jobsearch.jobapplication.application;

import com.example.jobsearch.jobapplication.domain.JobApplicationStatus;
import com.example.jobsearch.jobapplication.web.*;
import org.springframework.data.domain.Pageable;

public interface JobApplicationService {
    JobApplicationResponse create(long currentUserId, JobApplicationRequest request);
    PageResponse<JobApplicationResponse> findMine(long currentUserId, Pageable pageable);
    PageResponse<JobApplicationResponse> findForVacancy(long vacancyId, long currentUserId, String role, Pageable pageable);
    JobApplicationResponse findById(long id, long currentUserId, String role);
    JobApplicationResponse updateStatus(long id, long currentUserId, String role, JobApplicationStatus status);
    void deleteOrWithdraw(long id, long currentUserId, String role);
    PageResponse<JobApplicationResponse> findAllForAdmin(Long vacancyId, JobApplicationStatus status, Pageable pageable);
}
