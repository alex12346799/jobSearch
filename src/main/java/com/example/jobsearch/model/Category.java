package com.example.jobsearch.model;

import lombok.Data;

@Data
public class Category {
    private long id;
    private String name;
    private Integer parentId;
}
