package com.example.jobsearch.storage.application;

import com.example.jobsearch.storage.domain.*;
import com.example.jobsearch.storage.infrastructure.StoredFileRepository;
import com.example.jobsearch.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoredFileMetadataService {
    private final StoredFileRepository repository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<String> replaceAvatar(User owner, String objectKey, String contentType, long size, Instant now) {
        StoredFile metadata = repository.findByOwnerIdAndPurpose(owner.getId(), StoredFilePurpose.AVATAR)
                .orElseGet(StoredFile::new);
        String oldKey = metadata.getObjectKey();
        metadata.setOwner(owner);
        metadata.setPurpose(StoredFilePurpose.AVATAR);
        metadata.setObjectKey(objectKey);
        metadata.setContentType(contentType);
        metadata.setSize(size);
        metadata.setCreatedAt(now);
        repository.saveAndFlush(metadata);
        return Optional.ofNullable(oldKey);
    }

    @Transactional(readOnly = true)
    public StoredFile requireAvatar(long ownerId) {
        return repository.findByOwnerIdAndPurpose(ownerId, StoredFilePurpose.AVATAR)
                .orElseThrow(AvatarNotFoundException::new);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<String> removeAvatar(long ownerId) {
        return repository.findByOwnerIdAndPurpose(ownerId, StoredFilePurpose.AVATAR).map(metadata -> {
            String key = metadata.getObjectKey();
            repository.delete(metadata);
            repository.flush();
            return key;
        });
    }
}
