package com.example.jobsearch.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@NoArgsConstructor
public class ResumeResponseDto {
    private long id;
    private Long applicantId;
    private String name;
    private Long categoryId;
    private double salary;
    private boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
