package com.example.jobsearch.dao;

import com.example.jobsearch.model.User;


import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(Long id);

    List<User> findByEmail(String email);

    List<User> findAll();

    void save(User user);

    void update(User user);

    void deleteById(Long id);

    Optional<User>  findByUsername(String username);
}
