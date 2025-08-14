package com.example.jobsearch.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@NoArgsConstructor
public class ResumeResponseDto {
    private Long id;
    private Long applicantId;
    private String applicantName;
    private String name;
    private String categoryName;
    private Long categoryId;
    private double salary;
    private boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
