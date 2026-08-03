package com.example.jobsearch.user.application;

import com.example.jobsearch.user.domain.User;
import com.example.jobsearch.user.web.UserProfileResponse;
import com.example.jobsearch.storage.domain.StoredFilePurpose;
import com.example.jobsearch.storage.infrastructure.StoredFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProfileMapper {
    private final StoredFileRepository storedFileRepository;

    public UserProfileResponse toResponse(User user) {
        return new UserProfileResponse(user.getId(), user.getName(), user.getSurname(), user.getAge(),
                user.getEmail(), user.getPhoneNumber(), user.getAddress(),
                storedFileRepository.existsByOwnerIdAndPurpose(user.getId(), StoredFilePurpose.AVATAR), user.getCompanyName(),
                user.getRole().getName(), user.isEnabled());
    }
}
