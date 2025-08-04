package com.example.jobsearch.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VacancyRequestDto {
    @NotBlank(message = "поле title не может быть пустым")
    private String title;

    @NotBlank(message = "поле description не может быть пустым")
    private String description;

    @NotNull(message = "Обязательно введите categoryId")
    private Integer categoryId;

    @DecimalMin(value = "0.0", inclusive = false, message = "Зарплата должна быть больше нуля")
    private double salary;

    @Min(value = 0, message = "Минимальный опыт не может быть меньше 0")
    private Integer expFrom;

    @Min(value = 0, message = "Максимальный опыт не может быть меньше 0")
    private Integer expTo;


    @NotNull(message = "Обязательно введите employerId")
    private Integer employerId;
}
