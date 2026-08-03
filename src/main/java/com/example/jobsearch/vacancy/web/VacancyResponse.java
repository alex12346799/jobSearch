package com.example.jobsearch.vacancy.web;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public record VacancyResponse(
        Long id,
        String title,
        String description,
        Long categoryId,
        String categoryName,
        BigDecimal salary,
        Integer expFrom,
        Integer expTo,
        boolean isActive,
        Long employerId,
        String employerName,
        LocalDateTime createdDate,
        LocalDateTime updateDate
) {
}
