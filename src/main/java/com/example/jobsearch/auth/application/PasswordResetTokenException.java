package com.example.jobsearch.auth.application;

public class PasswordResetTokenException extends RuntimeException {
    public PasswordResetTokenException() {
        super("The password reset token is invalid or expired");
    }
}
