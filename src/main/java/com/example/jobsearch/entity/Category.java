package com.example.jobsearch.entity;

import lombok.Data;

@Data
public class Category {
    private int id;
    private String name;
    private Integer parentId;
}
