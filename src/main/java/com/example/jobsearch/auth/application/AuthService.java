package com.example.jobsearch.auth.application;

import com.example.jobsearch.auth.web.LoginRequest;
import com.example.jobsearch.auth.web.LogoutRequest;
import com.example.jobsearch.auth.web.RefreshTokenRequest;
import com.example.jobsearch.auth.web.RegistrationRequest;
import com.example.jobsearch.auth.web.RegistrationResponse;
import com.example.jobsearch.auth.web.TokenResponse;
import com.example.jobsearch.auth.web.ForgotPasswordRequest;
import com.example.jobsearch.auth.web.ForgotPasswordResponse;
import com.example.jobsearch.auth.web.ResetPasswordRequest;
import org.springframework.security.core.Authentication;

public interface AuthService {
    RegistrationResponse registerApplicant(RegistrationRequest request);
    RegistrationResponse registerEmployer(RegistrationRequest request);
    TokenResponse login(LoginRequest request);
    TokenResponse refresh(RefreshTokenRequest request);
    void logout(LogoutRequest request, Authentication authentication);
    void logoutAll(Authentication authentication);
    ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
}
