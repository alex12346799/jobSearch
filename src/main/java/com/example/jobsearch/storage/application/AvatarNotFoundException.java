package com.example.jobsearch.storage.application;

public class AvatarNotFoundException extends RuntimeException {
    public AvatarNotFoundException() {
        super("Avatar was not found");
    }
}
