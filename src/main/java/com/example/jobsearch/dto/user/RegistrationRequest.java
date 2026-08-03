package com.example.jobsearch.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Locale;

public record RegistrationRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must not exceed 255 characters")
        String name,

        @NotBlank(message = "Surname is required")
        @Size(max = 255, message = "Surname must not exceed 255 characters")
        String surname,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 45, message = "Email must not exceed 45 characters")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 72, message = "Password must contain between 8 and 72 characters")
        String password,

        @Size(max = 255, message = "Company name must not exceed 255 characters")
        String companyName
) {
    public RegistrationRequest {
        name = trim(name);
        surname = trim(surname);
        email = email == null ? null : email.trim().toLowerCase(Locale.ROOT);
        companyName = trim(companyName);
    }

    private static String trim(String value) {
        return value == null ? null : value.trim();
    }
}
