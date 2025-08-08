package com.example.jobsearch.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RespondentApplicantResponseDto {
    private Long id;
    private int resumeId;
    private int vacancyId;
    private boolean confirmation;
    private LocalDateTime createDate;
}
