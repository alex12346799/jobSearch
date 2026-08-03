package com.example.jobsearch.auth.application;

public class AuthForbiddenException extends RuntimeException {
    public AuthForbiddenException() {
        super("The user account is disabled");
    }
}
