package com.example.jobsearch.service;

import com.example.jobsearch.dto.UserRequestDto;
import com.example.jobsearch.dto.UserResponseDto;
import com.example.jobsearch.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    User getUserById(Long id);

   UserResponseDto getByEmail(String email);

    List<User> getAllUsers();


    User login(String email, String password);

    void updateUser(UserRequestDto dto);

    void deleteUser(long id);

    void register(UserRequestDto dto);
}
