package com.example.jobsearch.resume.web;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record EducationInfoRequest(
        @NotBlank @Size(max = 255) String institution,
        @NotBlank @Size(max = 255) String program,
        @NotNull @PastOrPresent LocalDate startDate,
        @NotNull @PastOrPresent LocalDate endDate,
        @NotBlank @Size(max = 255) String degree
) implements StrictResumeRequest {
    public EducationInfoRequest {
        institution = trim(institution); program = trim(program); degree = trim(degree);
    }
    private static String trim(String value) { return value == null ? null : value.trim(); }
}
