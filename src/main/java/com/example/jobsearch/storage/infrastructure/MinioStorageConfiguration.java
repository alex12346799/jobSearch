package com.example.jobsearch.storage.infrastructure;

import com.example.jobsearch.storage.application.StorageUnavailableException;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

@Configuration
@EnableConfigurationProperties(MinioStorageProperties.class)
public class MinioStorageConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = "storage.minio", name = "enabled", havingValue = "true", matchIfMissing = true)
    MinioClient minioClient(MinioStorageProperties properties) {
        return MinioClient.builder().endpoint(properties.endpoint())
                .credentials(properties.accessKey(), properties.secretKey()).build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "storage.minio", name = "enabled", havingValue = "true", matchIfMissing = true)
    ObjectStorage objectStorage(MinioClient client, MinioStorageProperties properties) {
        return new MinioObjectStorage(client, properties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "storage.minio", name = "enabled", havingValue = "true", matchIfMissing = true)
    ApplicationRunner initializePrivateBucket(MinioClient client, MinioStorageProperties properties) {
        return arguments -> {
            try {
                boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(properties.bucket()).build());
                if (!exists) client.makeBucket(MakeBucketArgs.builder().bucket(properties.bucket()).build());
                try {
                    client.deleteBucketPolicy(DeleteBucketPolicyArgs.builder().bucket(properties.bucket()).build());
                } catch (ErrorResponseException exception) {
                    if (!"NoSuchBucketPolicy".equals(exception.errorResponse().code())) throw exception;
                }
            } catch (Exception exception) {
                throw new StorageUnavailableException(exception);
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(ObjectStorage.class)
    ObjectStorage unavailableObjectStorage() {
        return new ObjectStorage() {
            public void put(String key, byte[] content, String type) { throw new StorageUnavailableException(); }
            public StoredObject get(String key) { throw new StorageUnavailableException(); }
            public void delete(String key) { throw new StorageUnavailableException(); }
        };
    }
}
