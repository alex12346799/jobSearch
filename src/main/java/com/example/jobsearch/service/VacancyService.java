package com.example.jobsearch.service;

import com.example.jobsearch.dto.VacancyRequestDto;
import com.example.jobsearch.dto.VacancyResponseDto;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.Vacancy;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VacancyService {



    Vacancy create(VacancyRequestDto vacancyRequestDto, Authentication auth);

    Vacancy getById(long id);

    List<VacancyResponseDto> getAll();

    List<Vacancy> findByEmployer(Authentication auth);


    void update(Vacancy vacancy, long id, Authentication auth);

    void delete(long id, Authentication auth);
}
