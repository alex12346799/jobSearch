package com.example.jobsearch.resume.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record ResumeRequest(
        @NotBlank @Size(max = 45) String name,
        @NotNull @Positive Long categoryId,
        @NotNull @PositiveOrZero Double salary,
        @NotNull Boolean active,
        @Valid List<EducationInfoRequest> education,
        @Valid List<WorkExperienceInfoRequest> workExperience,
        @Valid SocialLinksRequest socialLinks
) implements StrictResumeRequest {
    public ResumeRequest {
        name = name == null ? null : name.trim();
        education = education == null ? List.of() : List.copyOf(education);
        workExperience = workExperience == null ? List.of() : List.copyOf(workExperience);
    }
}
