package com.example.jobsearch.service;

import com.example.jobsearch.dto.user.UserRegisterRequest;
import com.example.jobsearch.model.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface RegistrationService {
    User registerApplicant(UserRegisterRequest dto, HttpServletRequest request);
    User registerEmployer(UserRegisterRequest dto, HttpServletRequest request);
    void sendResetPasswordLink(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException;
    void updatePassword(String token, String newPassword);
}
