package com.example.jobsearch.repository;

import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByApplicant(User applicant);

}
