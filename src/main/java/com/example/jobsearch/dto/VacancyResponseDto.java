package com.example.jobsearch.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class VacancyResponseDto {
    private Long id;
    private String title;
    private String description;
    private String categoryName;
    private Long categoryId;
    private double salary;
    private Integer expFrom;
    private Integer expTo;
    private boolean isActive;
    private Long employerId;
    private String employerName;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
