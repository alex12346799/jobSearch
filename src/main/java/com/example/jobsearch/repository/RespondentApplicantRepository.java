package com.example.jobsearch.repository;

import com.example.jobsearch.model.RespondentApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespondentApplicantRepository extends JpaRepository<RespondentApplicant, Long> {
}
