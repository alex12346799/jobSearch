package com.example.jobsearch.auth.application;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "auth.password-reset")
public record PasswordResetProperties(String baseUrl, Duration tokenTtl) {
}
