package com.example.jobsearch.storage.infrastructure;

public class StorageObjectNotFoundException extends RuntimeException {
    public StorageObjectNotFoundException(Throwable cause) {
        super(cause);
    }
}
