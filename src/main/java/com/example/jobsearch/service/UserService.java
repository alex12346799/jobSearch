package com.example.jobsearch.service;

import com.example.jobsearch.model.User;
import com.example.jobsearch.storage.UserStorage;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }
    public List<User> findAll() {
        return userStorage.findAll();
    }
    public Optional<User> findById(Long id) {
        return userStorage.findById(id);
    }
    public Optional<User> findEmployerId(int id){
        Optional<User> user = userStorage.findById(id);
        if(user.isPresent()&&"EMPLOYER".equalsIgnoreCase(user.get().getRole())){
            return user;
        }else {
            return Optional.empty();
        }
    }

    public User register(User user) {
    return userStorage.save(user);
}
}
