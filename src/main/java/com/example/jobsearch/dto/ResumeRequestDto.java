package com.example.jobsearch.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ResumeRequestDto {
    @NotNull(message = "Нужно обязательно указать applicantId")
    private Long applicantId;

    @NotBlank(message = "Имя не должно быть пустым")
    private String name;

    @NotNull(message = "Нужно обязательно указать categoryId")
    private Long categoryId;

    @NotNull(message = "Укажите активна ли вакансия или нет")
    private  boolean isActive;

    @DecimalMin(value = "0.0", inclusive = false, message = "Зарплата должна быть больше нуля")
    private double salary;
    private List<EducationInfoRequestDto> educationInfoList;
    private List<WorkExperienceInfoRequestDto> workExperienceInfoList;
    private SocialLinkRequestDto socialLinkRequestDto;


}
