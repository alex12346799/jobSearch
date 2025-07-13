package com.example.jobsearch.storage;

import com.example.jobsearch.model.User;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long userId = 1;

    public User save(User user) {
        user.setId(userId++);
        users.put(user.getId(), user);
        return user;
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> findById(long id) {
        return Optional.ofNullable(users.get(id));
    }
}
