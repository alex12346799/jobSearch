package com.example.jobsearch.jobapplication.web;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record JobApplicationRequest(
        @NotNull @Positive Long vacancyId,
        @NotNull @Positive Long resumeId
) implements StrictJobApplicationRequest {
}
