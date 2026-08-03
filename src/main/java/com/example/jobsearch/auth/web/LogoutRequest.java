package com.example.jobsearch.auth.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LogoutRequest(@NotBlank @Size(max = 512) String refreshToken) implements StrictAuthRequest {
}
