package com.example.jobsearch.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data

public class Vacancy {
    private Long id;
    private String title;
    private String description;
    private Long categoryId;
    private double salary;
    private Integer expFrom;
    private Integer expTo;
    private boolean isActive;
    private Long employerId;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
