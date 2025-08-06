package com.example.jobsearch.dao.impl;

import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> rowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setRole(rs.getString("role"));
        user.setAge(rs.getInt("age"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setAddress(rs.getString("address"));
        user.setAvatar(rs.getString("avatar"));
        user.setAccountType(rs.getString("account_type"));
        return user;
    };


    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM USERS WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, rowMapper, id);
        return users.stream().findFirst();

    }
    @Override
    public List<User> findByEmail(String email) {
        String sql = "SELECT * FROM USERS WHERE email = ?";
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), email);
    }


    @Override
    public List<User> findAll() {

        return jdbcTemplate.query("SELECT * FROM USERS", rowMapper);
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO USERS (name, surname, role,age, email, password, phone_number, address, avatar, account_type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getName(),
                user.getSurname(),
                user.getRole(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getAvatar(),
                user.getAccountType()
        );
    }




    @Override
    public void update(User user) {
        String sql = "UPDATE users SET name=?, surname=?, role=?, age=?, email=?, password=?, phone_number=?, address=?, avatar=?, account_type=? WHERE id=?";
        jdbcTemplate.update(sql,
                user.getName(),
                user.getSurname(),
                user.getRole(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getAvatar(),
                user.getAccountType(),
                user.getId()
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", id);
    }

    @Override
    public Optional <User> findByUsername(String username) {
        String sql = "SELECT * FROM USERS WHERE username = ?";
      List<User> users= jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class),username);
     return users.stream().findFirst();
    }
}
