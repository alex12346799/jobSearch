package com.example.jobsearch.user.application;

import com.example.jobsearch.user.web.ChangePasswordRequest;
import com.example.jobsearch.user.web.UpdateProfileRequest;
import com.example.jobsearch.user.web.UserProfileResponse;

public interface UserProfileService {
    UserProfileResponse getProfile(long userId);
    UserProfileResponse updateProfile(long userId, UpdateProfileRequest request);
    void changePassword(long userId, ChangePasswordRequest request);
}
