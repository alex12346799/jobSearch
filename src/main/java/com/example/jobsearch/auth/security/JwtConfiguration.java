package com.example.jobsearch.auth.security;

import com.example.jobsearch.auth.application.PasswordResetProperties;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Clock;
import java.time.Duration;
import java.util.Base64;
import java.security.SecureRandom;

@Configuration
@EnableConfigurationProperties({JwtProperties.class, PasswordResetProperties.class})
public class JwtConfiguration {
    @Bean
    public Clock authClock() {
        return Clock.systemUTC();
    }

    @Bean
    public SecureRandom authSecureRandom() {
        return new SecureRandom();
    }

    @Bean
    public SecretKey jwtSecretKey(JwtProperties properties) {
        try {
            byte[] decoded = Base64.getDecoder().decode(properties.secret());
            if (decoded.length < 32) {
                throw new IllegalStateException("JWT secret must contain at least 32 decoded bytes");
            }
            return new SecretKeySpec(decoded, "HmacSHA256");
        } catch (IllegalArgumentException | NullPointerException exception) {
            throw new IllegalStateException("JWT secret must be valid Base64 with at least 32 decoded bytes");
        }
    }

    @Bean
    public JwtEncoder jwtEncoder(SecretKey secretKey) {
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
    }

    @Bean
    public JwtDecoder jwtDecoder(SecretKey secretKey, Clock authClock) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(secretKey).build();
        JwtTimestampValidator timestampValidator = new JwtTimestampValidator(Duration.ZERO);
        timestampValidator.setClock(authClock);
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(timestampValidator));
        return decoder;
    }
}
