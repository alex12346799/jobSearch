package com.example.jobsearch.vacancy.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record VacancyRequest(
        @NotBlank(message = "Title must not be blank")
        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,
        @NotBlank(message = "Description must not be blank")
        @Size(max = 255, message = "Description must not exceed 255 characters")
        String description,
        @NotNull(message = "Category id is required")
        @Positive(message = "Category id must be positive")
        Long categoryId,
        @Positive(message = "Salary must be positive")
        double salary,
        @PositiveOrZero(message = "Minimum experience must not be negative")
        Integer expFrom,
        @PositiveOrZero(message = "Maximum experience must not be negative")
        Integer expTo,
        @NotNull(message = "Active status is required")
        Boolean isActive
) {
}
