package com.example.jobsearch.dto1;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class WorkExperienceInfoDto {

    @NotBlank(message = "Название компании обязательно")
    private String companyName;

    @Positive(message = "Опыт работы должен быть положительным")
    private int years;

    @NotBlank(message = "Должность обязательна")
    private String position;
}
