package com.example.jobsearch.mapper;

import com.example.jobsearch.dto.VacancyRequestDto;
import com.example.jobsearch.dto.VacancyResponseDto;
import com.example.jobsearch.model.Category;
import com.example.jobsearch.model.User;
import com.example.jobsearch.model.Vacancy;

public class VacancyMapper {


    public static VacancyResponseDto toDto(Vacancy vacancy) {
        if (vacancy == null) return null;
        VacancyResponseDto dto = new VacancyResponseDto();
        dto.setId(vacancy.getId());
        dto.setTitle(vacancy.getTitle());
        dto.setDescription(vacancy.getDescription());
        dto.setCategoryId(vacancy.getCategory().getId());
        dto.setSalary(vacancy.getSalary());
        dto.setExpFrom(vacancy.getExpFrom());
        dto.setExpTo(vacancy.getExpTo());
        dto.setActive(vacancy.isActive());
        dto.setEmployerId(vacancy.getEmployer().getId());
        dto.setCreatedDate(vacancy.getCreatedDate());
        dto.setUpdateDate(vacancy.getUpdateDate());
        return dto;
    }

    public static Vacancy fromDto(VacancyRequestDto dto, Category category, User employer) {
        if (dto == null) return null;
        Vacancy vacancy = new Vacancy();
        vacancy.setTitle(dto.getTitle());
        vacancy.setDescription(dto.getDescription());
        vacancy.setCategory(category);
        vacancy.setSalary(dto.getSalary());
        vacancy.setExpFrom(dto.getExpFrom());
        vacancy.setExpTo(dto.getExpTo());
        vacancy.setEmployer(employer);
        vacancy.setActive(dto.isActive());

        return vacancy;
    }
}
