package com.example.jobsearch.dto;

import jakarta.validation.constraints.NotNull;

import lombok.Data;


@Data
public class RespondentApplicantRequestDto {
    @NotNull(message = "Обязательно укажите resumeId")
    private Long resumeId;

    @NotNull(message = "Обязательно укажите vacancyId")
    private Long vacancyId;

}
