package com.example.jobsearch.dao.impl;

import com.example.jobsearch.dao.WorkExperienceInfoDao;
import com.example.jobsearch.model.WorkExperienceInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class WorkExperienceInfoDaoImpl implements WorkExperienceInfoDao {
   private final JdbcTemplate jdbcTemplate;

    public WorkExperienceInfoDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveAll(List<WorkExperienceInfo> workExperienceInfos, int resumeId) {
     String sql = "INSERT INTO work_experience_info(resume_id, years, company_name, position, responsibilities)"+
             "VALUES(?,?,?,?,?)";
     for (WorkExperienceInfo workExperienceInfo : workExperienceInfos) {
         jdbcTemplate.update(sql,resumeId,
                 workExperienceInfo.getYears(),
                 workExperienceInfo.getCompanyName(),
                 workExperienceInfo.getPosition(),
                 workExperienceInfo.getResponsibilities());
     }
    }
}
