package com.example.jobsearch.repository;

import com.example.jobsearch.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findAllByApplicantId(int applicantId);

}
