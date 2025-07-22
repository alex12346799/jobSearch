package com.example.jobsearch.dao;

import com.example.jobsearch.model.WorkExperienceInfo;

import java.util.List;

public interface WorkExperienceInfoDao {
    void saveAll(List<WorkExperienceInfo> workExperienceInfos, int resumeId);
}
