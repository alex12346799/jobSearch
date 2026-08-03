package com.example.jobsearch.auth.web;

import com.example.jobsearch.user.domain.RoleName;

public record RegistrationResponse(Long id, String name, String surname, String email,
                                   String companyName, RoleName role) {
}
