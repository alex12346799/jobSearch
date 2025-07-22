package com.example.jobsearch.dao;

import com.example.jobsearch.model.EducationInfo;

import java.util.List;

public interface EducationInfoDao {
    void saveAll(List<EducationInfo> educationInfoList, int resumeId);
}
