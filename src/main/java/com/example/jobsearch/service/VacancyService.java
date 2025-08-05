package com.example.jobsearch.service;

import com.example.jobsearch.dto.VacancyRequestDto;
import com.example.jobsearch.model.Vacancy;

import java.util.List;

public interface VacancyService {

    void create(VacancyRequestDto vacancyRequestDto);


    Vacancy getById(long id);

    List<Vacancy> getAll();

    List<Vacancy> getByUser(int userId);

    void update(Vacancy vacancy);

    void delete(int id);

    void delete(long id);
}
