package com.example.jobsearch.storage.application;

import com.example.jobsearch.auth.application.*;
import com.example.jobsearch.storage.infrastructure.*;
import com.example.jobsearch.storage.web.AvatarResponse;
import com.example.jobsearch.user.domain.User;
import com.example.jobsearch.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Clock;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvatarService {
    private final UserRepository userRepository;
    private final AvatarFileValidator validator;
    private final ObjectStorage objectStorage;
    private final StoredFileMetadataService metadataService;
    private final Clock authClock;

    public AvatarResponse upload(long userId, MultipartFile file) {
        User user = currentUser(userId);
        var validated = validator.validate(file);
        String newKey = "avatars/" + userId + "/" + UUID.randomUUID();
        objectStorage.put(newKey, validated.content(), validated.contentType());
        java.util.Optional<String> oldKey;
        try {
            oldKey = metadataService.replaceAvatar(user, newKey, validated.contentType(),
                    validated.content().length, authClock.instant());
        } catch (RuntimeException exception) {
            try { objectStorage.delete(newKey); } catch (RuntimeException compensationFailure) {
                exception.addSuppressed(compensationFailure);
            }
            throw exception;
        }
        oldKey.filter(key -> !key.equals(newKey)).ifPresent(objectStorage::delete);
        var metadata = metadataService.requireAvatar(userId);
        return new AvatarResponse(true, metadata.getContentType(), metadata.getSize(), metadata.getCreatedAt());
    }

    public AvatarContent get(long userId) {
        currentUser(userId);
        var metadata = metadataService.requireAvatar(userId);
        try {
            var object = objectStorage.get(metadata.getObjectKey());
            return new AvatarContent(object.content(), metadata.getContentType());
        } catch (StorageObjectNotFoundException exception) {
            metadataService.removeAvatar(userId);
            throw new AvatarNotFoundException();
        }
    }

    public void delete(long userId) {
        currentUser(userId);
        metadataService.removeAvatar(userId).ifPresent(objectStorage::delete);
    }

    private User currentUser(long id) {
        User user = userRepository.findById(id).orElseThrow(AuthUnauthorizedException::new);
        if (!user.isEnabled()) throw new AuthForbiddenException();
        return user;
    }

    public record AvatarContent(byte[] bytes, String contentType) {}
}
