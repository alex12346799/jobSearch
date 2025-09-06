package com.example.jobsearch.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ResumeRequestDto {

@NotNull(message = "{resume.applicantId.notnull}")
private Long applicantId;

    @NotBlank(message = "{resume.name.notblank}")
    private String name;

    @NotNull(message = "{resume.categoryId.notnull}")
    private Long categoryId;

    @NotNull(message = "{resume.isActive.notnull}")
    private Boolean isActive;

    @DecimalMin(value = "0.0", inclusive = false, message = "{resume.salary.min}")
    @NotNull(message = "{resume.salary.notnull}")
    private Double salary;


    @Valid
    private List<EducationInfoRequestDto> educationInfoList;
    @Valid
    private List<WorkExperienceInfoRequestDto> workExperienceInfoList;
    @Valid
    private SocialLinkRequestDto socialLinkRequestDto;
}

