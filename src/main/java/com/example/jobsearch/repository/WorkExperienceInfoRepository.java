package com.example.jobsearch.repository;

import com.example.jobsearch.model.WorkExperienceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperienceInfoRepository extends JpaRepository<WorkExperienceInfo, Long> {
}
