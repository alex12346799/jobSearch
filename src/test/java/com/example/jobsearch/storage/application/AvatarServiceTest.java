package com.example.jobsearch.storage.application;

import com.example.jobsearch.storage.infrastructure.ObjectStorage;
import com.example.jobsearch.user.domain.User;
import com.example.jobsearch.user.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.time.Clock;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AvatarServiceTest {
    @Test
    void databaseFailureCompensatesNewObject() {
        UserRepository users = mock(UserRepository.class);
        AvatarFileValidator validator = mock(AvatarFileValidator.class);
        ObjectStorage storage = mock(ObjectStorage.class);
        StoredFileMetadataService metadata = mock(StoredFileMetadataService.class);
        User user = new User(); user.setId(42L); user.setEnabled(true);
        byte[] bytes = {1, 2, 3};
        when(users.findById(42L)).thenReturn(java.util.Optional.of(user));
        when(validator.validate(any())).thenReturn(new AvatarFileValidator.ValidatedAvatar(bytes, "image/png"));
        when(metadata.replaceAvatar(eq(user), anyString(), eq("image/png"), eq(3L), any()))
                .thenThrow(new IllegalStateException("database failed"));
        AvatarService service = new AvatarService(users, validator, storage, metadata, Clock.systemUTC());

        assertThatThrownBy(() -> service.upload(42L,
                new MockMultipartFile("file", "avatar.png", "image/png", bytes)))
                .isInstanceOf(IllegalStateException.class);
        verify(storage).put(matches("avatars/42/[0-9a-f-]{36}"), same(bytes), eq("image/png"));
        verify(storage).delete(matches("avatars/42/[0-9a-f-]{36}"));
    }
}
