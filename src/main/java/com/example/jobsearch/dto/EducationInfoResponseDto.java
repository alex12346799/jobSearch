package com.example.jobsearch.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EducationInfoResponseDto {
    private int id;
    private int resumeId;
    private String institution;
    private String program;
    private LocalDate startDate;
    private LocalDate endDate;
    private String degree;
}
