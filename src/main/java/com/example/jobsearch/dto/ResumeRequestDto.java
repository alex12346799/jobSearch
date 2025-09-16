package com.example.jobsearch.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeRequestDto {

    private Long applicantId;
    private String name;
    private Long categoryId;
    private Boolean isActive;
    private Double salary;


    private List<EducationInfoRequestDto> educationInfoList;
    private List<WorkExperienceInfoRequestDto> workExperienceInfoList;
    private SocialLinkRequestDto socialLinkRequestDto = new SocialLinkRequestDto();
}

