package com.example.jobsearch.storage.infrastructure;

public interface ObjectStorage {
    void put(String objectKey, byte[] content, String contentType);
    StoredObject get(String objectKey);
    void delete(String objectKey);

    record StoredObject(byte[] content, String contentType) {}
}
