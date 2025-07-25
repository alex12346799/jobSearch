package com.example.jobsearch.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class ResumeCreateDto {

    @NotNull(message = "ID соискателя обязателен")
    private Integer applicantId;

    @NotBlank(message = "Название резюме обязательно")
    @Size(min = 2, max = 200, message = "Название резюме должно быть от 2 до 200 символов")
    private String name;

    @NotNull(message = "Категория обязательна")
    private Integer categoryId;

    @NotNull(message = "Зарплата обязательна")
    @Positive(message = "Зарплата должна быть положительной")
    private Double salary;

    private Boolean isActive;

    private List<@Valid EducationInfoDto> educationInfoList;

    private List<@Valid WorkExperienceInfoDto> workExperienceInfoList;
}
