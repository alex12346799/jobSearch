package com.example.jobsearch.model;



import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;

@Data
public class Resume {
    private long id;
    private Long applicantId;
    private String name;
    private Long categoryId;
    private double salary;
    private boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private List<EducationInfo> educationInfoList;
    private List<WorkExperienceInfo> workExperienceInfoList;



}
