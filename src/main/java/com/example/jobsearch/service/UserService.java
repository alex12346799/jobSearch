package com.example.jobsearch.service;

import com.example.jobsearch.dto.UserRequestDto;
import com.example.jobsearch.model.User;

import java.util.List;

public interface UserService {
    User getUserById(Long id);

    List<User> getAllUsers();




    void createUser(UserRequestDto dto);

    void updateUser(long id, UserRequestDto dto);

    void deleteUser(long id);

}
