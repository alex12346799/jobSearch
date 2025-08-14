package com.example.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDto {
    @NotBlank(message = "Имя не может быть пустым. Введите имя")
    private String name;

    private Long parentId;
}
