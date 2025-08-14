package com.example.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkExperienceInfoRequestDto {
    @NotNull(message = "Обязательно укажите resumeId")
    private Long resumeId;

    @NotBlank(message = "Обязательно укажите количество лет")
    private Long years;

    @NotBlank(message = "Поле companyName не должен быть пустым")
    private String companyName;

    @NotBlank(message = "Поле position не должен быть пустым")
    private String position;

    @NotBlank(message = "Поле responsibilities не должен быть пустым")
    private String responsibilities;
}
