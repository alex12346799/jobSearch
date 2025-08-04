package com.example.jobsearch.dto;

import lombok.Data;

@Data
public class WorkExperienceInfoResponseDto {
    private long id;
    private int resumeId;
    private int years;
    private String companyName;
    private String position;
    private String responsibilities;
}
