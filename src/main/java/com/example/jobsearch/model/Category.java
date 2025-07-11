package com.example.jobsearch.model;

import lombok.Data;

@Data
public class Category {
    private int id;
    private String name;
    private Integer parentId;
}
