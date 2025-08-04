package com.example.jobsearch.mapper;

import com.example.jobsearch.dto1.VacancyCreateDto;
import com.example.jobsearch.model.Vacancy;

public class VacancyMapper {
    public static Vacancy fromTo(VacancyCreateDto vacancyCreateDto) {
        Vacancy vacancy = new Vacancy();
        vacancy.setTitle(vacancyCreateDto.getTitle());
        vacancy.setDescription(vacancyCreateDto.getDescription());
        vacancy.setCategoryId(vacancyCreateDto.getCategoryId());
        vacancy.setSalary(vacancyCreateDto.getSalary());
        vacancy.setExpFrom(vacancyCreateDto.getExpFrom());
        vacancy.setExpTo(vacancyCreateDto.getExpTo());
//        vacancy.set(vacancyCreateDto.isAsActive());
        vacancy.setEmployerId(vacancyCreateDto.getEmployerId());
        vacancy.setCreatedDate(vacancyCreateDto.getCreatedDate());
        vacancy.setUpdateDate(vacancyCreateDto.getUpdatedDate());
        return vacancy;
    }
}
