package com.example.jobsearch.repository;

import com.example.jobsearch.model.User;
import com.example.jobsearch.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findByEmployerId(long employerId);

}
