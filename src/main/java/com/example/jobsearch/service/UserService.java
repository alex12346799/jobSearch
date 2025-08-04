package com.example.jobsearch.service;

import com.example.jobsearch.dto1.UserCreateDto;
import com.example.jobsearch.model.User;

import java.util.List;

public interface UserService {
    User getUserById(Long id);

    List<User> getAllUsers();


    void createUser(UserCreateDto userCreateDto);

    void updateUser(long id, UserCreateDto dto);

    void deleteUser(long id);

}
