package com.example.jobsearch.repository;

import com.example.jobsearch.model.User;
import com.example.jobsearch.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findByEmployerId(long employerId);

}
