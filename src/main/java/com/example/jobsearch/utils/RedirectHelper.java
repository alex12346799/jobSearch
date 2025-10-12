
package com.example.jobsearch.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class RedirectHelper {

    public void redirectByRole(Collection<? extends GrantedAuthority> authorities,
                               HttpServletResponse response) throws IOException {

        String redirect = getRedirectByRole(authorities).replace("redirect:", "");


        if (!response.isCommitted()) {
            response.sendRedirect(redirect);
        }
    }

    public String getRedirectByRole(Collection<? extends GrantedAuthority> authorities) {
        if (hasRole(authorities, "EMPLOYEE")) return "redirect:/resumes";
        if (hasRole(authorities, "APPLICANT")) return "redirect:/vacancies";
        return "redirect:/login";
    }

    private boolean hasRole(Collection<? extends GrantedAuthority> authorities, String role) {
        return authorities.stream()
                .anyMatch(a -> a.getAuthority().equalsIgnoreCase(role));
    }
}

