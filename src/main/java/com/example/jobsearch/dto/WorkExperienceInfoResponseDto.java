package com.example.jobsearch.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkExperienceInfoResponseDto {
    private long id;
    private Long resumeId;

    private LocalDate startDate;


    private LocalDate endDate;
    private String companyName;
    private String position;
    private String responsibilities;
}
