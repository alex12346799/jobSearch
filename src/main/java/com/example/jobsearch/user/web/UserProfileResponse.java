package com.example.jobsearch.user.web;

import com.example.jobsearch.user.domain.RoleName;

public record UserProfileResponse(
        Long id,
        String name,
        String surname,
        Integer age,
        String email,
        String phoneNumber,
        String address,
        boolean avatarAvailable,
        String companyName,
        RoleName role,
        boolean enabled
) {
}
