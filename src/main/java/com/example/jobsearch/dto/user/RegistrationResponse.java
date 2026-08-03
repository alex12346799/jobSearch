package com.example.jobsearch.dto.user;

import com.example.jobsearch.model.RoleName;

public record RegistrationResponse(
        Long id,
        String name,
        String surname,
        String email,
        String companyName,
        RoleName role
) {
}
