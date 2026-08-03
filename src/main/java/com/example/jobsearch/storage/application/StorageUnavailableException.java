package com.example.jobsearch.storage.application;

public class StorageUnavailableException extends RuntimeException {
    public StorageUnavailableException() {
        super("File storage is temporarily unavailable");
    }

    public StorageUnavailableException(Throwable cause) {
        super("File storage is temporarily unavailable", cause);
    }
}
