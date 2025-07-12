package com.example.jobsearch.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class Resume {
    private int id;
    private int applicantId;
    private String name;
    private int categoryId;
    private double salary;
    private boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}
