package com.example.jobsearch.model;

import lombok.Data;

@Data
public class WorkExperienceInfo {
    private int id;
    private int resumeId;
    private int years;
    private String companyName;
    private String position;
    private String responsibilities;
}
