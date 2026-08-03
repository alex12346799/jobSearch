package com.example.jobsearch.resume.application;

import com.example.jobsearch.resume.web.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResumeService {
    PageResponse<ResumeResponse> findAll(String search, Long categoryId, Pageable pageable, String role);
    ResumeResponse findById(long id, long currentUserId, String role);
    List<ResumeResponse> findMine(long currentUserId);
    ResumeResponse create(long currentUserId, ResumeRequest request);
    ResumeResponse update(long id, long currentUserId, String role, ResumeRequest request);
    void delete(long id, long currentUserId, String role);
}
