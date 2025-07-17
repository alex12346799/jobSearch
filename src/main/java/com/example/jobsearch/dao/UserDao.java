package com.example.jobsearch.dao;

import com.example.jobsearch.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findByName(String name) {
        String sql = "SELECT * FROM users where name = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), name);
    }


    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
    }

    public List<User> findByPhone(String phone) {
        String sql = "SELECT * FROM users WHERE PHONE_NUMBER = ?";
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class),phone);
    }
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
    public void save(User user) {
        String sql = "INSERT INTO users (name, surname, role, age, email, password) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getRole(), user.getAge(), user.getEmail(), user.getPassword());
    }

    public void update(User user) {
        String sql = "UPDATE users SET name = ?, surname = ?, role = ?, age = ?, email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getRole(), user.getAge(), user.getEmail(), user.getPassword(), user.getId());
    }

    public void delete(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
