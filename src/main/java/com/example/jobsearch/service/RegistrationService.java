package com.example.jobsearch.service;

import com.example.jobsearch.dto.user.RegistrationRequest;
import com.example.jobsearch.dto.user.RegistrationResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface RegistrationService {
    RegistrationResponse registerApplicant(RegistrationRequest request);
    RegistrationResponse registerEmployer(RegistrationRequest request);
    void sendResetPasswordLink(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException;
    void updatePassword(String token, String newPassword);
}
