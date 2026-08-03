package com.example.jobsearch.auth.application;

public class PasswordResetValidationException extends RuntimeException {
    public PasswordResetValidationException(String message) {
        super(message);
    }
}
