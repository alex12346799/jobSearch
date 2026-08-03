package com.example.jobsearch.exceptions;

public class SystemRoleMissingException extends RuntimeException {
    public SystemRoleMissingException() {
        super("A required system role is not configured");
    }
}
