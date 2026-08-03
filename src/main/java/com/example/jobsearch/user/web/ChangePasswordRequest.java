package com.example.jobsearch.user.web;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank(message = "Current password is required") String currentPassword,
        @NotBlank(message = "New password is required") @Size(min = 8, max = 72) String newPassword,
        @NotBlank(message = "Password confirmation is required") @Size(min = 8, max = 72) String confirmPassword
) {
    @JsonAnySetter
    public void rejectUnknownField(String field, Object value) {
        throw new IllegalArgumentException("Unknown password request field: " + field);
    }
}
