package com.example.jobsearch.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponseDto {
    private int respondedApplicants;
    private String content;
    private LocalDateTime timestamp;
}
