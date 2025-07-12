package com.example.jobsearch.entity;

import lombok.Data;

@Data
public class ContactInfo {
    private int id;
    private int typeId;
    private int resumeId;
    private String value;
}
