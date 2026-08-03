package com.example.jobsearch.category.application.exception;

public class CategoryNameConflictException extends CategoryConflictException {
    public CategoryNameConflictException(String name) {
        super("Category name '" + name + "' is already in use");
    }
}
