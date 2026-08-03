package com.example.jobsearch.category.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "Category name must not be blank")
        @Size(max = 45, message = "Category name must not exceed 45 characters")
        String name,
        @Positive(message = "Parent id must be positive")
        Long parentId
) {
}
