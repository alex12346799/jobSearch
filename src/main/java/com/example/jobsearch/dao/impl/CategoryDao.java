package com.example.jobsearch.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryDao {
private final JdbcTemplate jdbcTemplate;


    public String findNameById(long id) {
        String sql = "select name from CATEGORY where id=?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }
}
