package com.example.jobsearch.jobapplication.web;

import com.example.jobsearch.jobapplication.domain.JobApplicationStatus;

import java.time.LocalDateTime;

public record JobApplicationResponse(
        Long id,
        VacancySummary vacancy,
        ResumeSummary resume,
        ApplicantSummary applicant,
        JobApplicationStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public record VacancySummary(Long id, String title, Long employerId) {}
    public record ResumeSummary(Long id, String name) {}
    public record ApplicantSummary(Long id, String name, String surname) {}
}
