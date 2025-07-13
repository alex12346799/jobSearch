package com.example.jobsearch.service;

import com.example.jobsearch.entity.User;
import com.example.jobsearch.storage.UserStorage;
import lombok.Builder;
import org.springframework.stereotype.Service;


@Builder
@Service
public class UserService {
    private final UserStorage userStorage;

public User register(User user) {
    return userStorage.save(user);
}
}
