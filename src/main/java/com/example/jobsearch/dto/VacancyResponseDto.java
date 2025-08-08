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
    private Long categoryId;
    private double salary;
    private int expFrom;
    private int expTo;
    private boolean isActive;
    private Long employerId;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
