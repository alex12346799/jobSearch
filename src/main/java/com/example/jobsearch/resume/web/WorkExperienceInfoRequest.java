package com.example.jobsearch.resume.web;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record WorkExperienceInfoRequest(
        @NotNull @PastOrPresent LocalDate startDate,
        @NotNull @PastOrPresent LocalDate endDate,
        @NotBlank @Size(max = 255) String companyName,
        @NotBlank @Size(max = 255) String position,
        @Size(max = 255) String responsibilities
) implements StrictResumeRequest {
    public WorkExperienceInfoRequest {
        companyName = trim(companyName); position = trim(position); responsibilities = trim(responsibilities);
    }
    private static String trim(String value) { return value == null ? null : value.trim(); }
}
