package com.example.jobsearch.auth.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JwtResourceServerIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private JwtEncoder jwtEncoder;

    @Test
    void validAccessTokenCanReachProtectedCategoryEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/categories").header("Authorization", "Bearer " + token(false)))
                .andExpect(status().isOk());
    }

    @Test
    void requestWithoutAccessTokenIsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/categories")).andExpect(status().isUnauthorized());
    }

    @Test
    void damagedAccessTokenIsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/categories").header("Authorization", "Bearer damaged.token.value"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void expiredAccessTokenIsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/categories").header("Authorization", "Bearer " + token(true)))
                .andExpect(status().isUnauthorized());
    }

    private String token(boolean expired) {
        Instant now = Instant.now();
        Instant issuedAt = expired ? now.minusSeconds(120) : now;
        Instant expiresAt = expired ? now.minusSeconds(60) : now.plusSeconds(300);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject("1")
                .claim("email", "applicant@example.com")
                .claim("role", "APPLICANT")
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .id(UUID.randomUUID().toString())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
    }
}
