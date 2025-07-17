package com.example.jobsearch.dao;

import com.example.jobsearch.model.Resume;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResumeDao {
    private final JdbcTemplate jdbcTemplate;

    public ResumeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Resume> findAll() {
        return jdbcTemplate.query("SELECT * FROM resume", new BeanPropertyRowMapper<>(Resume.class));
    }
    public List<Resume> findByApplicantId(int id){
        return jdbcTemplate.query("SELECT * FROM RESUME where applicant_Id"
                , new BeanPropertyRowMapper<>(Resume.class),id);
    }
    public List<Resume> findCategoeyId(int categoryId){
        return jdbcTemplate.query("SELECT * FROM resume WHERE category_id = ?",
                new BeanPropertyRowMapper<>(Resume.class),categoryId);
    }
    public void save(Resume resume) {
        String sql = "INSERT INTO resume (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        jdbcTemplate.update(sql, resume.getApplicantId(), resume.getName(), resume.getCategoryId(), resume.getSalary(), resume.isActive());
    }
    public void update(Resume resume) {
        String sql = "UPDATE resume SET name = ?, category_id = ?, salary = ?, is_active = ?, update_time = CURRENT_TIMESTAMP WHERE id = ?";
        jdbcTemplate.update(sql, resume.getName(), resume.getCategoryId(), resume.getSalary(), resume.isActive(), resume.getId());
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM resume WHERE id = ?", id);
    }


}
