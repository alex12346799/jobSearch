package com.example.jobsearch.auth.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Locale;

public record RegistrationRequest(
        @NotBlank(message = "Name is required") @Size(max = 255) String name,
        @NotBlank(message = "Surname is required") @Size(max = 255) String surname,
        @NotBlank(message = "Email is required") @Email(message = "Email must be valid") @Size(max = 45) String email,
        @NotBlank(message = "Password is required") @Size(min = 8, max = 72) String password,
        @Size(max = 255) String companyName
) implements StrictAuthRequest {
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
