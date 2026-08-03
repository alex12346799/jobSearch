package com.example.jobsearch.auth.application;

import com.example.jobsearch.auth.domain.RefreshToken;
import com.example.jobsearch.auth.persistence.RefreshTokenRepository;
import com.example.jobsearch.auth.security.JwtProperties;
import com.example.jobsearch.auth.security.JwtTokenService;
import com.example.jobsearch.auth.web.LoginRequest;
import com.example.jobsearch.auth.web.LogoutRequest;
import com.example.jobsearch.auth.web.RefreshTokenRequest;
import com.example.jobsearch.auth.web.TokenResponse;
import com.example.jobsearch.model.Role;
import com.example.jobsearch.model.RoleName;
import com.example.jobsearch.model.User;
import com.example.jobsearch.repository.RoleRepository;
import com.example.jobsearch.repository.UserRepository;
import com.example.jobsearch.service.impl.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HexFormat;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    private static final Instant NOW = Instant.parse("2026-08-03T00:00:00Z");
    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private RefreshTokenRepository refreshTokenRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtTokenService jwtTokenService;
    @Mock private SecureRandom secureRandom;
    @Mock private EmailService emailService;
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(userRepository, roleRepository, refreshTokenRepository,
                passwordEncoder, jwtTokenService, new JwtProperties("unused", Duration.ofMinutes(15), Duration.ofDays(30)),
                Clock.fixed(NOW, ZoneOffset.UTC), secureRandom, emailService);
        lenient().doAnswer(invocation -> {
            byte[] bytes = invocation.getArgument(0);
            for (int index = 0; index < bytes.length; index++) bytes[index] = (byte) (index + 1);
            return null;
        }).when(secureRandom).nextBytes(any(byte[].class));
    }

    @Test void logsInApplicant() { assertLogin(RoleName.APPLICANT); }
    @Test void logsInEmployer() { assertLogin(RoleName.EMPLOYER); }
    @Test void logsInAdmin() { assertLogin(RoleName.ADMIN); }

    @Test
    void normalizesLoginEmailAndStoresOnlyRefreshHash() {
        User user = user(1L, RoleName.APPLICANT, true);
        when(userRepository.findByEmailIgnoreCase("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);
        when(jwtTokenService.createAccessToken(user)).thenReturn("access");

        TokenResponse response = authService.login(new LoginRequest(" User@Example.COM ", "password"));

        ArgumentCaptor<RefreshToken> captor = ArgumentCaptor.forClass(RefreshToken.class);
        verify(refreshTokenRepository).save(captor.capture());
        assertThat(response.refreshToken()).isNotEqualTo(captor.getValue().getTokenHash());
        assertThat(captor.getValue().getTokenHash()).hasSize(64).isEqualTo(hash(response.refreshToken()));
    }

    @Test
    void invalidEmailAndPasswordUseSameUnauthorizedException() {
        when(userRepository.findByEmailIgnoreCase("missing@example.com")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authService.login(new LoginRequest("missing@example.com", "password")))
                .isExactlyInstanceOf(AuthUnauthorizedException.class);

        User user = user(1L, RoleName.APPLICANT, true);
        when(userRepository.findByEmailIgnoreCase("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", user.getPassword())).thenReturn(false);
        assertThatThrownBy(() -> authService.login(new LoginRequest("user@example.com", "wrong")))
                .isExactlyInstanceOf(AuthUnauthorizedException.class);
    }

    @Test
    void disabledUserIsForbidden() {
        User user = user(1L, RoleName.APPLICANT, false);
        when(userRepository.findByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);
        assertThatThrownBy(() -> authService.login(new LoginRequest(user.getEmail(), "password")))
                .isExactlyInstanceOf(AuthForbiddenException.class);
    }

    @Test
    void refreshRotatesAndRevokesOldToken() {
        User user = user(1L, RoleName.EMPLOYER, true);
        RefreshToken old = token(user, NOW.plusSeconds(60));
        when(refreshTokenRepository.findByTokenHash(hash("old-token"))).thenReturn(Optional.of(old));
        when(refreshTokenRepository.saveAndFlush(any())).thenAnswer(invocation -> {
            RefreshToken saved = invocation.getArgument(0); saved.setId(2L); return saved;
        });
        when(jwtTokenService.createAccessToken(user)).thenReturn("new-access");

        TokenResponse response = authService.refresh(new RefreshTokenRequest("old-token"));

        assertThat(old.getUsedAt()).isEqualTo(NOW);
        assertThat(old.getRevokedAt()).isEqualTo(NOW);
        assertThat(old.getReplacedByToken()).isNotNull();
        assertThat(response.accessToken()).isEqualTo("new-access");
    }

    @Test
    void reusedOrExpiredRefreshTokenIsUnauthorized() {
        User user = user(1L, RoleName.APPLICANT, true);
        RefreshToken used = token(user, NOW.plusSeconds(60));
        used.setUsedAt(NOW.minusSeconds(1));
        when(refreshTokenRepository.findByTokenHash(hash("used"))).thenReturn(Optional.of(used));
        assertThatThrownBy(() -> authService.refresh(new RefreshTokenRequest("used")))
                .isExactlyInstanceOf(AuthUnauthorizedException.class);

        RefreshToken expired = token(user, NOW);
        when(refreshTokenRepository.findByTokenHash(hash("expired"))).thenReturn(Optional.of(expired));
        assertThatThrownBy(() -> authService.refresh(new RefreshTokenRequest("expired")))
                .isExactlyInstanceOf(AuthUnauthorizedException.class);
    }

    @Test
    void logoutRevokesTokenAndLogoutAllRevokesEveryActiveToken() {
        User user = user(1L, RoleName.APPLICANT, true);
        RefreshToken token = token(user, NOW.plusSeconds(60));
        when(refreshTokenRepository.findByTokenHash(hash("raw"))).thenReturn(Optional.of(token));
        when(userRepository.findByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));
        TestingAuthenticationToken authentication = new TestingAuthenticationToken(user.getEmail(), "n/a");

        authService.logout(new LogoutRequest("raw"), authentication);
        authService.logoutAll(authentication);

        assertThat(token.getRevokedAt()).isEqualTo(NOW);
        verify(refreshTokenRepository).revokeAllActiveByUserId(1L, NOW);
    }

    @Test
    void passwordChangeRevokesAllRefreshTokens() {
        User user = user(1L, RoleName.APPLICANT, true);
        when(userRepository.findByResetPasswordToken("reset")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("new-password")).thenReturn("encoded-new");

        authService.updatePassword("reset", "new-password");

        assertThat(user.getPassword()).isEqualTo("encoded-new");
        verify(refreshTokenRepository).revokeAllActiveByUserId(1L, NOW);
    }

    private void assertLogin(RoleName roleName) {
        User user = user(1L, roleName, true);
        when(userRepository.findByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);
        when(jwtTokenService.createAccessToken(user)).thenReturn("access");
        TokenResponse response = authService.login(new LoginRequest(user.getEmail(), "password"));
        assertThat(response.accessToken()).isEqualTo("access");
        verify(refreshTokenRepository).save(any());
    }

    private User user(Long id, RoleName roleName, boolean enabled) {
        Role role = new Role(); role.setName(roleName);
        User user = new User(); user.setId(id); user.setEmail("user@example.com");
        user.setPassword("encoded"); user.setRole(role); user.setEnabled(enabled); return user;
    }

    private RefreshToken token(User user, Instant expiresAt) {
        RefreshToken token = new RefreshToken(); token.setUser(user); token.setCreatedAt(NOW.minusSeconds(10));
        token.setExpiresAt(expiresAt); token.setTokenHash("hash"); return token;
    }

    private String hash(String value) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception exception) {
            throw new AssertionError(exception);
        }
    }
}
