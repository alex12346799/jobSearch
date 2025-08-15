package com.example.jobsearch.repository;

import com.example.jobsearch.model.EducationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationInfoRepository extends JpaRepository<EducationInfo, Long> {
}
