package com.example.jobsearch.model;

import lombok.Data;

@Data
public class ContactInfo {

    private Long id;
    private int typeId;
    private int resumeId;
    private String value;
}
