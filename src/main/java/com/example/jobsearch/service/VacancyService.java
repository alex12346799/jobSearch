package com.example.jobsearch.service;

import com.example.jobsearch.dto.VacancyCreateDto;
import com.example.jobsearch.model.Vacancy;

import java.util.List;

public interface VacancyService {

    void create(VacancyCreateDto vacancyCreateDto);

    Vacancy getById(int id);

    List<Vacancy> getAll();

    List<Vacancy> getByUser(int userId);

    void update(Vacancy vacancy);

    void delete(int id);
}
