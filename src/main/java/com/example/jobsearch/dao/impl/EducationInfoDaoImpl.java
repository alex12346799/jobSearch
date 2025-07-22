package com.example.jobsearch.dao.impl;

import com.example.jobsearch.dao.EducationInfoDao;
import com.example.jobsearch.model.EducationInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EducationInfoDaoImpl implements EducationInfoDao {
    private final JdbcTemplate jdbcTemplate;

    public EducationInfoDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveAll(List<EducationInfo> educationInfoList, int resumeId) {
        String sql = "INSERT INTO education_info(resume_id, institution ,program,start_date,end_date,degree)" +
                " VALUES (?, ?,?,?,?,?)";
        for (EducationInfo educationInfo : educationInfoList) {
            jdbcTemplate.update(sql, resumeId,
                    educationInfo.getInstitution(),
                    educationInfo.getProgram(),
                    educationInfo.getStartDate(),
                    educationInfo.getEndDate(),
                    educationInfo.getDegree()
            );

        }
    }
}
