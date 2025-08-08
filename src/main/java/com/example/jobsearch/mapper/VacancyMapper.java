package com.example.jobsearch.mapper;

import com.example.jobsearch.dto.VacancyRequestDto;
import com.example.jobsearch.dto.VacancyResponseDto;
import com.example.jobsearch.model.Vacancy;

public class VacancyMapper {


    public static VacancyResponseDto toDto(Vacancy vacancy) {
        if (vacancy == null) return null;
        VacancyResponseDto dto = new VacancyResponseDto();
        dto.setId(vacancy.getId());
        dto.setTitle(vacancy.getTitle());
        dto.setDescription(vacancy.getDescription());
        dto.setCategoryId(vacancy.getCategoryId());
        dto.setSalary(vacancy.getSalary());
        dto.setExpFrom(vacancy.getExpFrom());
        dto.setExpTo(vacancy.getExpTo());
        dto.setActive(vacancy.isActive());
        dto.setEmployerId(vacancy.getEmployerId());
        dto.setCreatedDate(vacancy.getCreatedDate());
        dto.setUpdateDate(vacancy.getUpdateDate());
        return dto;
    }

    public static Vacancy fromDto(VacancyRequestDto dto) {
        if (dto == null) return null;
        Vacancy vacancy = new Vacancy();
        vacancy.setTitle(dto.getTitle());
        vacancy.setDescription(dto.getDescription());
        vacancy.setCategoryId(dto.getCategoryId());
        vacancy.setSalary(dto.getSalary());
        vacancy.setExpFrom(dto.getExpFrom());
        vacancy.setExpTo(dto.getExpTo());
        vacancy.setEmployerId(dto.getEmployerId());
        return vacancy;
    }
}
