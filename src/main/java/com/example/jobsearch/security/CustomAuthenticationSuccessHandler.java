package com.example.jobsearch.security;

import com.example.jobsearch.utils.RedirectHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectHelper redirectHelper;

    public CustomAuthenticationSuccessHandler(RedirectHelper redirectHelper) {
        this.redirectHelper = redirectHelper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        redirectHelper.redirectByRole(authentication.getAuthorities(), response);
    }
}
