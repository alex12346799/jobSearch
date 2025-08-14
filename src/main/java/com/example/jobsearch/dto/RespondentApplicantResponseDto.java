package com.example.jobsearch.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RespondentApplicantResponseDto {
    private Long id;
    private Long resumeId;
    private Long vacancyId;
    private boolean confirmation;
    private LocalDateTime createDate;
}
