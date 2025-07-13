package com.example.jobsearch.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Vacancy {
    private int id;
    private String title;
    private String description;
    private int categoryId;
    private double salary;
    private int expFrom;
    private int expTo;
    private boolean isActive;
    private int employerId;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}
