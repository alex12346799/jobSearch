package com.example.jobsearch.dao.impl;

import com.example.jobsearch.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryDao {
private final JdbcTemplate jdbcTemplate;


    public String findNameById(long id) {
        String sql = "select name from CATEGORY where id=?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    public Category findById(long id) {
        String sql = "select id, name from CATEGORY where id=?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Category c = new Category();
                c.setId(rs.getLong("id"));
                c.setName(rs.getString("name"));
                return c;
            }, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<Category> findAll() {
        String sql = "select * from CATEGORY";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

}
