package com.example.jobsearch.user.application;

import com.example.jobsearch.auth.application.AuthForbiddenException;
import com.example.jobsearch.auth.application.AuthUnauthorizedException;
import com.example.jobsearch.auth.persistence.RefreshTokenRepository;
import com.example.jobsearch.user.domain.User;
import com.example.jobsearch.user.persistence.UserRepository;
import com.example.jobsearch.user.web.ChangePasswordRequest;
import com.example.jobsearch.user.web.UpdateProfileRequest;
import com.example.jobsearch.user.web.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserRepository userRepository;
    private final UserProfileMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final Clock authClock;

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(long userId) {
        return mapper.toResponse(currentUser(userId));
    }

    @Override
    @Transactional
    public UserProfileResponse updateProfile(long userId, UpdateProfileRequest request) {
        User user = currentUser(userId);
        if (request.name() != null) user.setName(request.name());
        if (request.surname() != null) user.setSurname(request.surname());
        if (request.age() != null) user.setAge(request.age());
        if (request.phoneNumber() != null) user.setPhoneNumber(request.phoneNumber());
        if (request.address() != null) user.setAddress(request.address());
        if (request.companyName() != null) user.setCompanyName(request.companyName());
        return mapper.toResponse(user);
    }

    @Override
    @Transactional
    public void changePassword(long userId, ChangePasswordRequest request) {
        User user = currentUser(userId);
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new UserProfileException("Current password is incorrect");
        }
        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new UserProfileException("Password confirmation does not match");
        }
        if (passwordEncoder.matches(request.newPassword(), user.getPassword())) {
            throw new UserProfileException("New password must differ from the current password");
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        refreshTokenRepository.revokeAllActiveByUserId(user.getId(), authClock.instant());
    }

    private User currentUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(AuthUnauthorizedException::new);
        if (!user.isEnabled()) throw new AuthForbiddenException();
        return user;
    }
}
