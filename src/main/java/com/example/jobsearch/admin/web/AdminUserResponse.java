package com.example.jobsearch.admin.web;

import com.example.jobsearch.user.domain.RoleName;

public record AdminUserResponse(Long id, String name, String surname, Integer age, String email,
                                String phoneNumber, String address, String companyName,
                                RoleName role, boolean enabled) { }
