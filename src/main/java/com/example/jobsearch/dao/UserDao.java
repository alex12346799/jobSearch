package com.example.jobsearch.dao;

import com.example.jobsearch.model.User;


import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(Long id);

    //    @Override
    //    public Optional<User> findById(Long id) {
    //        String sql = "SELECT * FROM USERS WHERE id = ?";
    //        List<User> users = jdbcTemplate.query(sql, rowMapper, id);
    //        return users.stream().findFirst();
    //
    //    }
    String findNameById(Long id);

    User findByEmail(String email);

    List<User> findAll();

    boolean findByName(String name);

    void save(User user);

    void update(User user);

    void deleteById(Long id);

    Optional<User>  findByUsername(String username);
}
