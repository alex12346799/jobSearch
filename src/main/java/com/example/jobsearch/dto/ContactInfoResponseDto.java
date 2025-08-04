package com.example.jobsearch.dto;

import lombok.Data;

@Data
public class ContactInfoResponseDto {
    private long id;
    private int typeId;
    private int resumeId;
    private String value;
}
