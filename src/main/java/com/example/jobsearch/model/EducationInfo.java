package com.example.jobsearch.model;

import lombok.Data;

import java.time.LocalDate;
@Data
public class EducationInfo {
    private int id;
    private Integer resumeId;
    private String institution;
    private String program;
    private LocalDate startDate;
    private LocalDate endDate;
    private String degree;
}
