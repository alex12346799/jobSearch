package com.example.jobsearch.dao;

import com.example.jobsearch.model.Vacancy;

import java.util.List;
import java.util.Optional;

public interface VacancyDao {
    void saveVacancy(Vacancy vacancy);

    Optional<Vacancy> findVacancyById(long id);


    List<Vacancy> findAllVacancies();

    List<Vacancy> findByUserId(int userId);

    void updateVacancy(Vacancy vacancy);

    void deleteVacancyById(long id);
}
