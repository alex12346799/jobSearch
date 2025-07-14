package com.example.jobsearch.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RespondentApplicant {
    private int id;
    private int resumeId;
    private int vacancyId;
    private boolean confirmation;
    private LocalDateTime createDate;
}
