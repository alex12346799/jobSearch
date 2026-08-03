package com.example.jobsearch.admin.web;

import com.example.jobsearch.auth.domain.RefreshToken;
import com.example.jobsearch.auth.persistence.RefreshTokenRepository;
import com.example.jobsearch.user.domain.*;
import com.example.jobsearch.user.persistence.*;
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

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AdminIntegrationTest {
    @Autowired MockMvc mockMvc;
    @Autowired JwtEncoder jwtEncoder;
    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;
    @Autowired RefreshTokenRepository refreshTokenRepository;
    @Autowired PasswordEncoder passwordEncoder;

    User admin;
    User applicant;

    @BeforeEach
    void setUp() {
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        admin = user("Admin", "Root", "admin." + suffix + "@example.com", RoleName.ADMIN, true);
        applicant = user("Searchable", "Applicant", "applicant." + suffix + "@example.com", RoleName.APPLICANT, true);
    }

    @Test
    void adminListsSearchesAndFiltersUsersAndGetsById() throws Exception {
        mockMvc.perform(get("/api/v1/admin/users").header("Authorization", bearer(admin))
                        .param("search", applicant.getEmail()).param("role", "APPLICANT").param("enabled", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(applicant.getId()))
                .andExpect(jsonPath("$.content[0].password").doesNotExist());
        mockMvc.perform(get("/api/v1/admin/users/{id}", applicant.getId())
                        .header("Authorization", bearer(admin)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.email").value(applicant.getEmail()));
    }

    @Test
    void onlyAdminCanAccess() throws Exception {
        mockMvc.perform(get("/api/v1/admin/users")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/v1/admin/users").header("Authorization", bearer(applicant)))
                .andExpect(status().isForbidden());
        User employer = user("Employer", null, "emp." + UUID.randomUUID().toString().substring(0, 8) + "@example.com",
                RoleName.EMPLOYER, true);
        mockMvc.perform(get("/api/v1/admin/users").header("Authorization", bearer(employer)))
                .andExpect(status().isForbidden());
    }

    @Test
    void disablingRevokesRefreshAndBlocksLoginThenEnablingRestoresLogin() throws Exception {
        RefreshToken token = new RefreshToken();
        token.setUser(applicant); token.setTokenHash(UUID.randomUUID().toString().replace("-", "").repeat(2)); token.setCreatedAt(Instant.now());
        token.setExpiresAt(Instant.now().plusSeconds(3600));
        token = refreshTokenRepository.saveAndFlush(token);

        mockMvc.perform(patch("/api/v1/admin/users/{id}/status", applicant.getId())
                        .header("Authorization", bearer(admin)).contentType("application/json")
                        .content("{\"enabled\":false}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.enabled").value(false));
        assertThat(refreshTokenRepository.findById(token.getId()).orElseThrow().getRevokedAt()).isNotNull();
        mockMvc.perform(post("/api/v1/auth/login").contentType("application/json")
                        .content("{\"email\":\"" + applicant.getEmail() + "\",\"password\":\"password123\"}"))
                .andExpect(status().isForbidden());

        mockMvc.perform(patch("/api/v1/admin/users/{id}/status", applicant.getId())
                        .header("Authorization", bearer(admin)).contentType("application/json")
                        .content("{\"enabled\":true}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.enabled").value(true));
        mockMvc.perform(post("/api/v1/auth/login").contentType("application/json")
                        .content("{\"email\":\"" + applicant.getEmail() + "\",\"password\":\"password123\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void adminCannotDisableSelfAndUnknownStatusFieldIsRejected() throws Exception {
        mockMvc.perform(patch("/api/v1/admin/users/{id}/status", admin.getId())
                        .header("Authorization", bearer(admin)).contentType("application/json")
                        .content("{\"enabled\":false}"))
                .andExpect(status().isConflict());
        mockMvc.perform(patch("/api/v1/admin/users/{id}/status", applicant.getId())
                        .header("Authorization", bearer(admin)).contentType("application/json")
                        .content("{\"enabled\":false,\"role\":\"ADMIN\"}"))
                .andExpect(status().isBadRequest());
    }

    private User user(String name, String surname, String email, RoleName roleName, boolean enabled) {
        Role role = roleRepository.findByName(roleName).orElseThrow();
        User user = new User(); user.setName(name); user.setSurname(surname); user.setEmail(email);
        user.setPassword(passwordEncoder.encode("password123")); user.setRole(role); user.setEnabled(enabled);
        return userRepository.saveAndFlush(user);
    }

    private String bearer(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder().subject(user.getId().toString()).claim("email", user.getEmail())
                .claim("role", user.getRole().getName().name()).issuedAt(now).expiresAt(now.plusSeconds(300))
                .id(UUID.randomUUID().toString()).build();
        return "Bearer " + jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
    }
}
