package com.example.jobsearch.auth.web;

import com.example.jobsearch.model.RoleName;

public record RegistrationResponse(Long id, String name, String surname, String email,
                                   String companyName, RoleName role) {
}
