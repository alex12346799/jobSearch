package com.example.jobsearch.category.application.exception;

public class CategoryInUseException extends CategoryConflictException {
    public CategoryInUseException(Long id) {
        super("Category with id " + id + " is in use and cannot be deleted");
    }
}
