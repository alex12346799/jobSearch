package com.example.jobsearch.user.application;

import com.example.jobsearch.user.domain.User;
import com.example.jobsearch.user.web.UserProfileResponse;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {
    public UserProfileResponse toResponse(User user) {
        return new UserProfileResponse(user.getId(), user.getName(), user.getSurname(), user.getAge(),
                user.getEmail(), user.getPhoneNumber(), user.getAddress(), user.getAvatar(), user.getCompanyName(),
                user.getRole().getName(), user.isEnabled());
    }
}
