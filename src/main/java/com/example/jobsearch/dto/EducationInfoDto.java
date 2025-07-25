package com.example.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EducationInfoDto {

    @NotBlank(message = "Название учебного заведения обязательно")
    private String institution;

    @NotBlank(message = "Программа обучения обязательна")
    private String program;

    @NotNull(message = "Дата начала обязательна")
    private String startDate;

    @NotNull(message = "Дата окончания обязательна")
    private String endDate;
}
