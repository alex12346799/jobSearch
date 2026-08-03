package com.example.jobsearch.user.web;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @Pattern(regexp = ".*\\S.*", message = "Name must not be blank") @Size(max = 255) String name,
        @Pattern(regexp = ".*\\S.*", message = "Surname must not be blank") @Size(max = 255) String surname,
        Integer age,
        @Pattern(regexp = ".*\\S.*", message = "Phone number must not be blank") @Size(max = 45) String phoneNumber,
        @Pattern(regexp = ".*\\S.*", message = "Address must not be blank") @Size(max = 255) String address,
        @Pattern(regexp = ".*\\S.*", message = "Company name must not be blank") @Size(max = 255) String companyName
) {
    public UpdateProfileRequest {
        name = trim(name);
        surname = trim(surname);
        phoneNumber = trim(phoneNumber);
        address = trim(address);
        companyName = trim(companyName);
    }

    @JsonAnySetter
    public void rejectUnknownField(String field, Object value) {
        throw new IllegalArgumentException("Unknown profile field: " + field);
    }

    private static String trim(String value) {
        return value == null ? null : value.trim();
    }
}
