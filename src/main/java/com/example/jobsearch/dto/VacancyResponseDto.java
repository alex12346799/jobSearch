package com.example.jobsearch.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VacancyResponseDto {
    private long id;
    private String title;
    private String description;
    private int categoryId;
    private double salary;
    private int expFrom;
    private int expTo;
    private boolean isActive;
    private int employerId;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
