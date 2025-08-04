package com.example.jobsearch.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResumeRequestDto {
    @NotNull(message = "Нужно обязательно указать applicantId")
    private Integer applicantId;

    @NotBlank(message = "Имя не должно быть пустым")
    private String name;

    @NotNull(message = "Нужно обязательно указать categoryId")
    private Integer categoryId;

    @DecimalMin(value = "0.0", inclusive = false, message = "Зарплата должна быть больше нуля")
    private double salary;

}
