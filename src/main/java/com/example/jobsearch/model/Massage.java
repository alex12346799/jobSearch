package com.example.jobsearch.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Massage {
    private int id;
    private int respondedApplicants;
    private String content;
    private LocalDateTime timestamp;
}
