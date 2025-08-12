package com.example.jobsearch.dao.impl;

import com.example.jobsearch.model.RespondentApplicant;
import com.example.jobsearch.model.WorkExperienceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
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
    public void saveAll(List<WorkExperienceInfo> workExperienceInfoList, long resumeId) {
        String sql = "INSERT INTO work_experience_info(resume_id, years, company_name, position, responsibilities) VALUES (?, ?, ?, ?, ?)";
        for (WorkExperienceInfo work : workExperienceInfoList) {
            jdbcTemplate.update(sql,
                    resumeId,
                    work.getYears(),
                    work.getCompanyName(),
                    work.getPosition(),
                    work.getResponsibilities()
            );
        }
    }

}
