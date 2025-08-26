package com.example.jobsearch.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
@Component
public class RedirectHelper {
    public void redirectByRole(Collection<? extends GrantedAuthority> authorities, HttpServletResponse response) throws IOException {
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("EMPLOYEE"))) {
            response.sendRedirect("/resumes");
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("APPLICANT"))) {
            response.sendRedirect("/vacancies");
        } else {
            response.sendRedirect("/login");
        }
    }

//    public void redirect(Collection<? extends GrantedAuthority> authorities,
//                         HttpServletResponse response) throws IOException {
//        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("EMPLOYEE"))) response.sendRedirect("/resumes");
//        response.sendRedirect("/vacancies");
//    }
}
