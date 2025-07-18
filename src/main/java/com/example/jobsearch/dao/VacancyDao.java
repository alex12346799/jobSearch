package com.example.jobsearch.dao;

import com.example.jobsearch.model.Vacancy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;

    public VacancyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Vacancy> findAll() {
        return jdbcTemplate.query("SELECT * FROM vacancy", new BeanPropertyRowMapper<>(Vacancy.class));
    }
public List<Vacancy> findByEmployerId(int employerId) {
        String sql = "SELECT * FROM vacancy WHERE employer_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), employerId);
}
public List<Vacancy> findByVacancyId(int categoryId) {
        String sql = "SELECT * FROM vacancy WHERE category_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), categoryId);
}
public Vacancy findById(int id) {
        String sql = "SELECT * FROM vacancy WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Vacancy.class), id);
}
    public void save(Vacancy vacancy) {
        String sql = "INSERT INTO vacancy (title, description, category_id, salary, exp_from, exp_to, is_active, employer_id, created_date, update_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        jdbcTemplate.update(sql, vacancy.getTitle(), vacancy.getDescription(), vacancy.getCategoryId(), vacancy.getSalary(),
                vacancy.getExpFrom(), vacancy.getExpTo(), vacancy.isActive(), vacancy.getEmployerId());
    }
    public void update(Vacancy vacancy) {
        String sql = "UPDATE vacancy SET title = ?, description = ?, category_id = ?, salary = ?, exp_from = ?, exp_to = ?, is_active = ?, employer_id = ?, update_time = CURRENT_TIMESTAMP WHERE id = ?";
        jdbcTemplate.update(sql, vacancy.getTitle(), vacancy.getDescription(), vacancy.getCategoryId(), vacancy.getSalary(),
                vacancy.getExpFrom(), vacancy.getExpTo(), vacancy.isActive(), vacancy.getEmployerId(), vacancy.getId());
    }

    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM vacancy WHERE id = ?",id) ;

    }
}
