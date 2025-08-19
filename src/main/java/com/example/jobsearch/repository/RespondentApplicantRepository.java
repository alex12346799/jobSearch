package com.example.jobsearch.repository;

import com.example.jobsearch.model.RespondentApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespondentApplicantRepository extends JpaRepository<RespondentApplicant, Long> {

    //    @Query(" SELECT r.va, COUNT(r) FROM RESPONDENT_APPLICANT r GROUP BY r.VACANCY_ID")
    @Query("SELECT r.vacancy.id, COUNT(r) FROM RespondentApplicant r GROUP BY r.vacancy.id")
    List<Object[]> countResponsesParVacancy();
}
