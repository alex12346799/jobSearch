package com.example.jobsearch.auth.web;

import com.example.jobsearch.auth.application.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register/applicant")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponse registerApplicant(@Valid @RequestBody RegistrationRequest request) {
        return authService.registerApplicant(request);
    }

    @PostMapping("/register/employer")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponse registerEmployer(@Valid @RequestBody RegistrationRequest request) {
        return authService.registerEmployer(request);
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refresh(request);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@Valid @RequestBody LogoutRequest request, Authentication authentication) {
        authService.logout(request, authentication);
    }

    @PostMapping("/logout-all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logoutAll(Authentication authentication) {
        authService.logoutAll(authentication);
    }
}
