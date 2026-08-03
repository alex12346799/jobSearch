package com.example.jobsearch.category.application.exception;

public abstract class CategoryConflictException extends RuntimeException {
    protected CategoryConflictException(String message) {
        super(message);
    }
}
