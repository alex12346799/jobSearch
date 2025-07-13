package com.example.jobsearch.service;

import com.example.jobsearch.model.User;
import com.example.jobsearch.storage.UserStorage;

import org.springframework.stereotype.Service;



@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User register(User user) {
    return userStorage.save(user);
}
}
