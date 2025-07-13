package com.example.jobsearch.model;

import lombok.Data;

@Data
public class RespondentApplicant {
    private int id;
    private int resumeId;
    private int vacancyId;
    private boolean confirmation;
}
