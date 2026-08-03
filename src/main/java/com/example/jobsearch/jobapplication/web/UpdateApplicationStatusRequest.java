package com.example.jobsearch.jobapplication.web;

import com.example.jobsearch.jobapplication.domain.JobApplicationStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateApplicationStatusRequest(
        @NotNull JobApplicationStatus status
) implements StrictJobApplicationRequest {
}
