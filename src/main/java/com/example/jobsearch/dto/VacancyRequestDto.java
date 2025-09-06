package com.example.jobsearch.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VacancyRequestDto {


    @NotBlank(message = "{createVacancies.validation.title}")
    private String title;

    @NotNull(message = "{createVacancies.validation.salary}")
    private Integer salary;

    @NotBlank(message = "{createVacancies.validation.description}")
    private String description;

    @NotNull(message = "Обязательно введите categoryId")
    private Long categoryId;



    @Min(value = 0, message = "Минимальный опыт не может быть меньше 0")
    private Integer expFrom;

    @Min(value = 0, message = "Максимальный опыт не может быть меньше 0")
    private Integer expTo;


    @NotNull(message = "Обязательно введите employerId")
    private Long employerId;

    @NotNull(message = "Укажите активна ли вакансия или нет")
    private Boolean isActive;
}
