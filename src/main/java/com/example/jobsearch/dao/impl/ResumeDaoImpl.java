package com.example.jobsearch.dao.impl;

import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ResumeDaoImpl implements ResumeDao {
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Resume> findAll() {
        String sql = "SELECT * FROM resume";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class));
    }

    @Override
    public Optional<Resume> findById(int id) {
        String sql = "SELECT * FROM resume WHERE id = ?";
        List<Resume> resumes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), id);
        return resumes.stream().findFirst();
    }

    @Override
    public List<Resume> findAllByApplicantId(int applicantId) {
        String sql = "SELECT * FROM resume WHERE applicant_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), applicantId);
    }

    @Override
    public void save(Resume resume) {
        String sql = "INSERT INTO resume (applicant_id, name, category_id, salary, is_active, created_date, update_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                resume.getApplicantId(),
                resume.getName(),
                resume.getCategoryId(),
                resume.getSalary(),
                resume.isActive(),
                resume.getCreatedDate(),
                resume.getUpdateTime()
        );
    }

    @Override
    public void update(Resume resume) {
        String sql = "UPDATE resume SET name = ?, category_id = ?, salary = ?, is_active = ?, update_date = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                resume.getName(),
                resume.getCategoryId(),
                resume.getSalary(),
                resume.isActive(),
                resume.getUpdateTime(),
                resume.getId()
        );
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM resume WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
