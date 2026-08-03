package com.example.jobsearch.user.web;

import com.example.jobsearch.auth.domain.RefreshToken;
import com.example.jobsearch.auth.persistence.RefreshTokenRepository;
import com.example.jobsearch.user.domain.Role;
import com.example.jobsearch.user.domain.RoleName;
import com.example.jobsearch.user.domain.User;
import com.example.jobsearch.user.persistence.RoleRepository;
import com.example.jobsearch.user.persistence.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserProfileIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private JwtEncoder jwtEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private RefreshTokenRepository refreshTokenRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private EntityManager entityManager;
    private User user;

    @BeforeEach
    void setUp() {
        Role role = roleRepository.findByName(RoleName.APPLICANT).orElseThrow();
        user = new User();
        user.setName("Profile"); user.setSurname("Owner"); user.setEmail("profile.test@example.com");
        user.setPassword(passwordEncoder.encode("old-password")); user.setRole(role); user.setEnabled(true);
        user = userRepository.saveAndFlush(user);
    }

    @Test
    void getsOwnProfileFromRealJwtAndRejectsMissingJwt() throws Exception {
        mockMvc.perform(get("/api/v1/users/me").header("Authorization", bearer()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.password").doesNotExist());
        mockMvc.perform(get("/api/v1/users/me")).andExpect(status().isUnauthorized());
    }

    @Test
    void disabledUserIsForbidden() throws Exception {
        user.setEnabled(false); userRepository.flush();
        mockMvc.perform(get("/api/v1/users/me").header("Authorization", bearer()))
                .andExpect(status().isForbidden());
    }

    @Test
    void patchesAndTrimsOnlyAllowedProfileFields() throws Exception {
        mockMvc.perform(patch("/api/v1/users/me").header("Authorization", bearer())
                        .contentType("application/json")
                        .content("{\"name\":\"  Updated  \",\"surname\":\"  Person  \"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"))
                .andExpect(jsonPath("$.surname").value("Person"));
    }

    @Test
    void rejectsBlankAndProtectedOrUnknownFields() throws Exception {
        mockMvc.perform(patch("/api/v1/users/me").header("Authorization", bearer())
                        .contentType("application/json").content("{\"name\":\"   \"}"))
                .andExpect(status().isBadRequest());
        for (String field : new String[]{"role", "enabled", "email", "userId"}) {
            mockMvc.perform(patch("/api/v1/users/me").header("Authorization", bearer())
                            .contentType("application/json").content("{\"" + field + "\":\"value\"}"))
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    void changesPasswordToBcryptAndRevokesRefreshToken() throws Exception {
        RefreshToken refresh = new RefreshToken(); refresh.setUser(user); refresh.setTokenHash("a".repeat(64));
        refresh.setCreatedAt(Instant.now()); refresh.setExpiresAt(Instant.now().plusSeconds(3600));
        refresh = refreshTokenRepository.saveAndFlush(refresh);

        mockMvc.perform(put("/api/v1/users/me/password").header("Authorization", bearer())
                        .contentType("application/json").content(passwordBody("old-password", "new-password", "new-password")))
                .andExpect(status().isNoContent());

        entityManager.flush(); entityManager.clear();
        User changed = userRepository.findById(user.getId()).orElseThrow();
        RefreshToken revoked = refreshTokenRepository.findById(refresh.getId()).orElseThrow();
        assertThat(changed.getPassword()).startsWith("$2");
        assertThat(passwordEncoder.matches("new-password", changed.getPassword())).isTrue();
        assertThat(passwordEncoder.matches("old-password", changed.getPassword())).isFalse();
        assertThat(revoked.getRevokedAt()).isNotNull();

        mockMvc.perform(post("/api/v1/auth/login").contentType("application/json")
                        .content("{\"email\":\"profile.test@example.com\",\"password\":\"old-password\"}"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(post("/api/v1/auth/login").contentType("application/json")
                        .content("{\"email\":\"profile.test@example.com\",\"password\":\"new-password\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void rejectsWrongCurrentMismatchShortAndSamePasswords() throws Exception {
        mockMvc.perform(put("/api/v1/users/me/password").header("Authorization", bearer())
                        .contentType("application/json").content(passwordBody("wrong-password", "new-password", "new-password")))
                .andExpect(status().isBadRequest());
        mockMvc.perform(put("/api/v1/users/me/password").header("Authorization", bearer())
                        .contentType("application/json").content(passwordBody("old-password", "new-password", "other-password")))
                .andExpect(status().isBadRequest());
        mockMvc.perform(put("/api/v1/users/me/password").header("Authorization", bearer())
                        .contentType("application/json").content(passwordBody("old-password", "short", "short")))
                .andExpect(status().isBadRequest());
        mockMvc.perform(put("/api/v1/users/me/password").header("Authorization", bearer())
                        .contentType("application/json").content(passwordBody("old-password", "old-password", "old-password")))
                .andExpect(status().isBadRequest());
    }

    private String bearer() {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder().subject(user.getId().toString()).claim("email", user.getEmail())
                .claim("role", "APPLICANT").issuedAt(now).expiresAt(now.plusSeconds(300))
                .id(UUID.randomUUID().toString()).build();
        return "Bearer " + jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
    }

    private String passwordBody(String current, String password, String confirmation) {
        return "{\"currentPassword\":\"" + current + "\",\"newPassword\":\"" + password
                + "\",\"confirmPassword\":\"" + confirmation + "\"}";
    }
}
