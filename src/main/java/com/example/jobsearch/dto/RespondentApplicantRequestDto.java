package com.example.jobsearch.dto;

import jakarta.validation.constraints.NotNull;

import lombok.Data;


@Data
public class RespondentApplicantRequestDto {
    @NotNull(message = "Обязательно укажите resumeId")
    private Integer resumeId;

    @NotNull(message = "Обязательно укажите vacancyId")
    private Integer vacancyId;

}
