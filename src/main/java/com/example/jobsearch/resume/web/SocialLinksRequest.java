package com.example.jobsearch.resume.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SocialLinksRequest(
        @NotBlank @Size(max = 255) String telegram,
        @NotBlank @Size(max = 255) String facebook,
        @NotBlank @Size(max = 255) String linkedin
) implements StrictResumeRequest {
    public SocialLinksRequest {
        telegram = trim(telegram); facebook = trim(facebook); linkedin = trim(linkedin);
    }
    private static String trim(String value) { return value == null ? null : value.trim(); }
}
