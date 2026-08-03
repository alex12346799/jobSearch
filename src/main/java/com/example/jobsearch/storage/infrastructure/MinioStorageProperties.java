package com.example.jobsearch.storage.infrastructure;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "storage.minio")
public record MinioStorageProperties(
        boolean enabled,
        String endpoint,
        String accessKey,
        String secretKey,
        String bucket,
        @Positive long avatarMaxSize
) {
    @AssertTrue(message = "MinIO endpoint, access key, secret key and bucket are required when storage is enabled")
    public boolean isCompleteWhenEnabled() {
        return !enabled || present(endpoint) && present(accessKey) && present(secretKey) && present(bucket)
                && secretKey.trim().length() >= 16;
    }

    private boolean present(String value) {
        return value != null && !value.isBlank();
    }
}
