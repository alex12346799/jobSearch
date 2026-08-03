package com.example.jobsearch.auth.application;

import com.example.jobsearch.auth.domain.RefreshToken;
import com.example.jobsearch.auth.domain.PasswordResetToken;
import com.example.jobsearch.auth.persistence.PasswordResetTokenRepository;
import com.example.jobsearch.auth.persistence.RefreshTokenRepository;
import com.example.jobsearch.auth.security.JwtProperties;
import com.example.jobsearch.auth.security.JwtTokenService;
import com.example.jobsearch.auth.web.LoginRequest;
import com.example.jobsearch.auth.web.LogoutRequest;
import com.example.jobsearch.auth.web.RefreshTokenRequest;
import com.example.jobsearch.auth.web.RegistrationRequest;
import com.example.jobsearch.auth.web.RegistrationResponse;
import com.example.jobsearch.auth.web.TokenResponse;
import com.example.jobsearch.auth.web.ForgotPasswordRequest;
import com.example.jobsearch.auth.web.ForgotPasswordResponse;
import com.example.jobsearch.auth.web.ResetPasswordRequest;
import com.example.jobsearch.exceptions.AlreadyExistsException;
import com.example.jobsearch.exceptions.SystemRoleMissingException;
import com.example.jobsearch.user.domain.Role;
import com.example.jobsearch.user.domain.RoleName;
import com.example.jobsearch.user.domain.User;
import com.example.jobsearch.user.persistence.RoleRepository;
import com.example.jobsearch.user.persistence.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final JwtProperties jwtProperties;
    private final PasswordResetProperties passwordResetProperties;
    private final Clock authClock;
    private final SecureRandom secureRandom;
    private final PasswordResetMailService passwordResetMailService;

    @Override
    @Transactional
    public RegistrationResponse registerApplicant(RegistrationRequest request) {
        return register(request, RoleName.APPLICANT);
    }

    @Override
    @Transactional
    public RegistrationResponse registerEmployer(RegistrationRequest request) {
        return register(request, RoleName.EMPLOYER);
    }

    @Override
    @Transactional
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(AuthUnauthorizedException::new);
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new AuthUnauthorizedException();
        }
        ensureEnabled(user);
        return issueTokenPair(user);
    }

    @Override
    @Transactional(noRollbackFor = AuthUnauthorizedException.class)
    public TokenResponse refresh(RefreshTokenRequest request) {
        Instant now = authClock.instant();
        RefreshToken current = refreshTokenRepository.findByTokenHash(hash(request.refreshToken()))
                .orElseThrow(AuthUnauthorizedException::new);
        if (current.getUsedAt() != null && current.getReplacedByToken() != null) {
            refreshTokenRepository.revokeAllActiveByUserId(current.getUser().getId(), now);
            throw new AuthUnauthorizedException();
        }
        if (current.getRevokedAt() != null || current.getUsedAt() != null || !current.getExpiresAt().isAfter(now)) {
            throw new AuthUnauthorizedException();
        }
        ensureEnabled(current.getUser());
        current.setUsedAt(now);
        current.setRevokedAt(now);
        IssuedRefresh replacement = createRefreshToken(current.getUser(), now);
        RefreshToken savedReplacement = refreshTokenRepository.saveAndFlush(replacement.entity());
        current.setReplacedByToken(savedReplacement);
        return tokenResponse(current.getUser(), replacement.rawToken());
    }

    @Override
    @Transactional
    public void logout(LogoutRequest request, Authentication authentication) {
        RefreshToken token = refreshTokenRepository.findByTokenHash(hash(request.refreshToken()))
                .orElseThrow(AuthUnauthorizedException::new);
        if (!token.getUser().getEmail().equalsIgnoreCase(authentication.getName())) {
            throw new AuthUnauthorizedException();
        }
        if (token.getRevokedAt() == null) {
            token.setRevokedAt(authClock.instant());
        }
    }

    @Override
    @Transactional
    public void logoutAll(Authentication authentication) {
        User user = userRepository.findByEmailIgnoreCase(authentication.getName())
                .orElseThrow(AuthUnauthorizedException::new);
        refreshTokenRepository.revokeAllActiveByUserId(user.getId(), authClock.instant());
    }

    @Override
    @Transactional
    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) {
        userRepository.findByEmailIgnoreCase(request.email())
                .filter(User::isEnabled)
                .ifPresent(this::createAndSendPasswordResetToken);
        return new ForgotPasswordResponse(
                "If the account is eligible, password reset instructions will be sent");
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new PasswordResetValidationException("Password confirmation does not match");
        }
        Instant now = authClock.instant();
        PasswordResetToken token = passwordResetTokenRepository.findByTokenHash(hash(request.token()))
                .orElseThrow(PasswordResetTokenException::new);
        if (token.getUsedAt() != null || token.getRevokedAt() != null || !token.getExpiresAt().isAfter(now)) {
            throw new PasswordResetTokenException();
        }
        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        token.setUsedAt(now);
        refreshTokenRepository.revokeAllActiveByUserId(user.getId(), now);
    }

    private void createAndSendPasswordResetToken(User user) {
        Instant now = authClock.instant();
        passwordResetTokenRepository.revokeAllActiveByUserId(user.getId(), now);
        byte[] random = new byte[32];
        secureRandom.nextBytes(random);
        String rawToken = Base64.getUrlEncoder().withoutPadding().encodeToString(random);
        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        token.setTokenHash(hash(rawToken));
        token.setCreatedAt(now);
        token.setExpiresAt(now.plus(passwordResetProperties.tokenTtl()));
        passwordResetTokenRepository.save(token);
        String baseUrl = passwordResetProperties.baseUrl().replaceAll("/+$", "");
        try {
            passwordResetMailService.send(user.getEmail(), baseUrl + "?token=" + rawToken);
        } catch (MessagingException | UnsupportedEncodingException ignored) {
            // The public response intentionally remains identical and does not expose account or mail state.
        }
    }

    private RegistrationResponse register(RegistrationRequest request, RoleName roleName) {
        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new AlreadyExistsException("A user with this email already exists");
        }
        Role role = roleRepository.findByName(roleName).orElseThrow(SystemRoleMissingException::new);
        User user = new User();
        user.setName(request.name());
        user.setSurname(request.surname());
        user.setEmail(request.email());
        user.setCompanyName(emptyToNull(request.companyName()));
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(role);
        user.setEnabled(true);
        User saved = userRepository.save(user);
        return new RegistrationResponse(saved.getId(), saved.getName(), saved.getSurname(), saved.getEmail(),
                saved.getCompanyName(), saved.getRole().getName());
    }

    private TokenResponse issueTokenPair(User user) {
        IssuedRefresh refresh = createRefreshToken(user, authClock.instant());
        refreshTokenRepository.save(refresh.entity());
        return tokenResponse(user, refresh.rawToken());
    }

    private TokenResponse tokenResponse(User user, String rawRefreshToken) {
        return new TokenResponse(jwtTokenService.createAccessToken(user), rawRefreshToken, "Bearer",
                jwtProperties.accessTokenTtl().toSeconds(), jwtProperties.refreshTokenTtl().toSeconds());
    }

    private IssuedRefresh createRefreshToken(User user, Instant now) {
        byte[] random = new byte[32];
        secureRandom.nextBytes(random);
        String raw = Base64.getUrlEncoder().withoutPadding().encodeToString(random);
        RefreshToken entity = new RefreshToken();
        entity.setUser(user);
        entity.setTokenHash(hash(raw));
        entity.setCreatedAt(now);
        entity.setExpiresAt(now.plus(jwtProperties.refreshTokenTtl()));
        return new IssuedRefresh(raw, entity);
    }

    private String hash(String rawToken) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                    .digest(rawToken.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is unavailable", exception);
        }
    }

    private void ensureEnabled(User user) {
        if (!user.isEnabled()) {
            throw new AuthForbiddenException();
        }
    }

    private String emptyToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }

    private record IssuedRefresh(String rawToken, RefreshToken entity) {
    }
}
