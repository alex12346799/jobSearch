package com.example.jobsearch.service;


import com.example.jobsearch.dto.user.UserEditRequest;
import com.example.jobsearch.dto.user.UserResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;


public interface UserService extends UserDetailsService {
    UserResponse getCurrentUser(Authentication authentication);
    void updateUser(UserEditRequest dto, Authentication authentication);
    void deleteUser(long id);
    UserResponse getByEmail(String email);
}
