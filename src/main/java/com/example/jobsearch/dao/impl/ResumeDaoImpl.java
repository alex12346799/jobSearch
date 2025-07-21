package com.example.jobsearch.dao.impl;

import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.model.Resume;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ResumeDaoImpl implements ResumeDao {
    private JdbcTemplate jdbcTemplate;

    @Override
    public Resume getById(Long id) {
        String sql = "SELECT * FROM RESUME WHERE id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Резюме с таким " + id + " не найден"));
    }

    @Override
    public List<Resume> findAll() {
        String sql = "SELECT * FROM RESUME";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class));
    }

    @Override
    public List<Resume> findByApplicantId(int applicantId) {
     String sql = "SELECT * FROM RESUME WHERE applicant_id = ?";
     return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), applicantId);
    }


    @Override
    public void save(Resume resume) {
        String sql = "INSERT INTO RESUME (applicant_id, name, category_id, salary, is_active, created_date, update_date)" +
                "VALUES (?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                resume.getApplicantId(),
                resume.getName(),
                resume.getCategoryId(),
                resume.getSalary(),
                resume.isActive(),
                resume.getCreatedDate(),
                resume.getUpdateTime());
    }

    @Override
    public void update(Resume resume) {
        String sql = "UPDATE RESUME SET name = ?, category_id = ?, salary = ?, is_active = ?, update_date = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                resume.getName(),
                resume.getCategoryId(),
                resume.getSalary(),
                resume.isActive(),
                resume.getUpdateTime(),
                resume.getId());
    }




    @Override
    public void delete(long id) {
        getById(id);
        String sql = "DELETE FROM RESUME WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }
}
