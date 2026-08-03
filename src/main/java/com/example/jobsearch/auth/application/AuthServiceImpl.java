package com.example.jobsearch.auth.application;

import com.example.jobsearch.auth.domain.RefreshToken;
import com.example.jobsearch.auth.persistence.RefreshTokenRepository;
import com.example.jobsearch.auth.security.JwtProperties;
import com.example.jobsearch.auth.security.JwtTokenService;
import com.example.jobsearch.auth.web.LoginRequest;
import com.example.jobsearch.auth.web.LogoutRequest;
import com.example.jobsearch.auth.web.RefreshTokenRequest;
import com.example.jobsearch.auth.web.RegistrationRequest;
import com.example.jobsearch.auth.web.RegistrationResponse;
import com.example.jobsearch.auth.web.TokenResponse;
import com.example.jobsearch.exceptions.AlreadyExistsException;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.exceptions.SystemRoleMissingException;
import com.example.jobsearch.model.Role;
import com.example.jobsearch.model.RoleName;
import com.example.jobsearch.model.User;
import com.example.jobsearch.repository.RoleRepository;
import com.example.jobsearch.repository.UserRepository;
import com.example.jobsearch.service.impl.EmailService;
import com.example.jobsearch.utils.Utility;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
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
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final JwtProperties jwtProperties;
    private final Clock authClock;
    private final SecureRandom secureRandom;
    private final EmailService emailService;

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
    @Transactional
    public TokenResponse refresh(RefreshTokenRequest request) {
        Instant now = authClock.instant();
        RefreshToken current = refreshTokenRepository.findByTokenHash(hash(request.refreshToken()))
                .orElseThrow(AuthUnauthorizedException::new);
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
    public void sendResetPasswordLink(HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email").trim().toLowerCase(Locale.ROOT);
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new NotFoundException("User was not found"));
        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);
        userRepository.save(user);
        emailService.sendEmail(email, Utility.makeSiteUrl(request) + "/auth/reset-password?token=" + token);
    }

    @Override
    @Transactional
    public void updatePassword(String token, String newPassword) {
        User user = userRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new NotFoundException("Invalid password reset token"));
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        refreshTokenRepository.revokeAllActiveByUserId(user.getId(), authClock.instant());
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
