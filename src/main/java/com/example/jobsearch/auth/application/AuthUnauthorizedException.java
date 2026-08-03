package com.example.jobsearch.auth.application;

public class AuthUnauthorizedException extends RuntimeException {
    public AuthUnauthorizedException() {
        super("Invalid authentication credentials or token");
    }
}
