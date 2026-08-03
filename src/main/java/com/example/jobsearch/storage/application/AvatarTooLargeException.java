package com.example.jobsearch.storage.application;

public class AvatarTooLargeException extends RuntimeException {
    public AvatarTooLargeException() {
        super("Avatar file is too large");
    }
}
