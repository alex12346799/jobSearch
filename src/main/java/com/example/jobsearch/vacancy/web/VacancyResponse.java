package com.example.jobsearch.vacancy.web;

import java.time.LocalDateTime;

public record VacancyResponse(
        Long id,
        String title,
        String description,
        Long categoryId,
        String categoryName,
        double salary,
        Integer expFrom,
        Integer expTo,
        boolean isActive,
        Long employerId,
        String employerName,
        LocalDateTime createdDate,
        LocalDateTime updateDate
) {
}
