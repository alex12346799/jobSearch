package com.example.jobsearch.entity;

import lombok.Data;

@Data
public class RespondentApplicant {
    private int id;
    private int resumeId;
    private int vacancyId;
    private boolean confirmation;
}
