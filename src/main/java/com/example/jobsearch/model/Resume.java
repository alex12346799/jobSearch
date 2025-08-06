package com.example.jobsearch.model;


import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Resume {
    private long id;
    private int applicantId;
    private String name;
    private int categoryId;
    private double salary;
    private boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
    private List<EducationInfo> educationInfoList;
    private List<WorkExperienceInfo> workExperienceInfoList;



}
