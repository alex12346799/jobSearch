package com.example.jobsearch.jobapplication.application;

import com.example.jobsearch.jobapplication.domain.JobApplication;
import com.example.jobsearch.jobapplication.web.JobApplicationResponse;
import org.springframework.stereotype.Component;

@Component
public class JobApplicationMapper {
    public JobApplicationResponse toResponse(JobApplication application) {
        var vacancy = application.getVacancy();
        var resume = application.getResume();
        var applicant = resume.getApplicant();
        return new JobApplicationResponse(
                application.getId(),
                new JobApplicationResponse.VacancySummary(vacancy.getId(), vacancy.getTitle(), vacancy.getEmployer().getId()),
                new JobApplicationResponse.ResumeSummary(resume.getId(), resume.getName()),
                new JobApplicationResponse.ApplicantSummary(applicant.getId(), applicant.getName(), applicant.getSurname()),
                application.getStatus(), application.getCreatedAt(), application.getUpdatedAt());
    }
}
