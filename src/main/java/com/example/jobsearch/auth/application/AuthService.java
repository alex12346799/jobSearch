package com.example.jobsearch.auth.application;

import com.example.jobsearch.auth.web.LoginRequest;
import com.example.jobsearch.auth.web.LogoutRequest;
import com.example.jobsearch.auth.web.RefreshTokenRequest;
import com.example.jobsearch.auth.web.RegistrationRequest;
import com.example.jobsearch.auth.web.RegistrationResponse;
import com.example.jobsearch.auth.web.TokenResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.io.UnsupportedEncodingException;

public interface AuthService {
    RegistrationResponse registerApplicant(RegistrationRequest request);
    RegistrationResponse registerEmployer(RegistrationRequest request);
    TokenResponse login(LoginRequest request);
    TokenResponse refresh(RefreshTokenRequest request);
    void logout(LogoutRequest request, Authentication authentication);
    void logoutAll(Authentication authentication);
    void sendResetPasswordLink(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException;
    void updatePassword(String token, String newPassword);
}
