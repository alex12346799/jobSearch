package com.example.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EducationInfoRequestDto {
    @NotNull(message = "Обязательно укажите resumeId")
    private Long resumeId;

    @NotBlank(message = "Поле institution не должен быть пустым")
    private String institution;

    @NotBlank(message = "Поле program не должен быть пустым")
    private String program;

    @NotNull(message = "Укажите дату начала")
    @PastOrPresent(message = "Дата начала не может быть в будущем")
    private LocalDate startDate;

    private LocalDate endDate;
    private String degree;
}
