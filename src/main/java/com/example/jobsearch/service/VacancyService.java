package com.example.jobsearch.service;

import com.example.jobsearch.dto.VacancyRequestDto;
import com.example.jobsearch.dto.VacancyResponseDto;
import com.example.jobsearch.model.Vacancy;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VacancyService {

    void create(VacancyRequestDto vacancyRequestDto);


    Vacancy getById(long id);

    List<VacancyResponseDto> getAll();

    List<Vacancy> getByUser(int userId);



    void update(Vacancy vacancy, long id, Authentication auth);

    void delete(long id, Authentication auth);
}
