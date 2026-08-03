package com.example.jobsearch.auth.web;

public record TokenResponse(String accessToken, String refreshToken, String tokenType,
                            long accessTokenExpiresIn, long refreshTokenExpiresIn) {
}
