package com.example.jobsearch.storage.infrastructure;

import com.example.jobsearch.storage.application.StorageUnavailableException;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;

@RequiredArgsConstructor
public class MinioObjectStorage implements ObjectStorage {
    private final MinioClient client;
    private final MinioStorageProperties properties;

    @Override
    public void put(String objectKey, byte[] content, String contentType) {
        try {
            client.putObject(PutObjectArgs.builder().bucket(properties.bucket()).object(objectKey)
                    .stream(new ByteArrayInputStream(content), content.length, -1).contentType(contentType).build());
        } catch (Exception exception) {
            throw new StorageUnavailableException(exception);
        }
    }

    @Override
    public StoredObject get(String objectKey) {
        try (var response = client.getObject(GetObjectArgs.builder()
                .bucket(properties.bucket()).object(objectKey).build())) {
            return new StoredObject(response.readAllBytes(), response.headers().get("Content-Type"));
        } catch (ErrorResponseException exception) {
            if ("NoSuchKey".equals(exception.errorResponse().code())) {
                throw new StorageObjectNotFoundException(exception);
            }
            throw new StorageUnavailableException(exception);
        } catch (Exception exception) {
            throw new StorageUnavailableException(exception);
        }
    }

    @Override
    public void delete(String objectKey) {
        try {
            client.removeObject(RemoveObjectArgs.builder().bucket(properties.bucket()).object(objectKey).build());
        } catch (Exception exception) {
            throw new StorageUnavailableException(exception);
        }
    }
}
