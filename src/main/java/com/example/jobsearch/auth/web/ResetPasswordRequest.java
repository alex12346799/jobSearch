package com.example.jobsearch.auth.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
        @NotBlank(message = "Token is required") @Size(max = 512) String token,
        @NotBlank(message = "New password is required") @Size(min = 8, max = 72) String newPassword,
        @NotBlank(message = "Password confirmation is required") @Size(min = 8, max = 72) String confirmPassword
) implements StrictAuthRequest {
}
