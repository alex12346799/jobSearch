package com.example.jobsearch.dao;

import com.example.jobsearch.model.RespondentApplicant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RespondentApplicationDao {
    private final JdbcTemplate jdbcTemplate;

    public RespondentApplicationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<RespondentApplicant> findAllByVacancyId(int vacancyId) {
        String sql = "SELECT * FROM respondent_applicant WHERE vacancy_Id=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RespondentApplicant.class), vacancyId);
    }
}
