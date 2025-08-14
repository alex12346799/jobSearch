package com.example.jobsearch.dto;

import lombok.Data;

@Data
public class WorkExperienceInfoResponseDto {
    private long id;
    private Long resumeId;
    private Long years;
    private String companyName;
    private String position;
    private String responsibilities;
}
