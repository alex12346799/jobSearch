package com.example.jobsearch.dao.impl;

import com.example.jobsearch.model.RespondentApplicant;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RespondentApplicantDao {
private final JdbcTemplate jdbcTemplate;
public Optional<RespondentApplicant> findById(Long applicantId) {
    String sql = "SELECT * FROM respondent_applicant WHERE id = ?";
    return Optional.ofNullable(
            DataAccessUtils.singleResult(
                    jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RespondentApplicant.class),
                            applicantId
                    )
            )
    );
}
}
