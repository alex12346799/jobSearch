package com.example.jobsearch.auth.web;

import com.example.jobsearch.auth.application.AuthService;
import com.example.jobsearch.auth.application.AuthUnauthorizedException;
import com.example.jobsearch.auth.security.RestAccessDeniedHandler;
import com.example.jobsearch.auth.security.RestAuthenticationEntryPoint;
import com.example.jobsearch.config.SecurityConfig;
import com.example.jobsearch.exceptions.AlreadyExistsException;
import com.example.jobsearch.model.RoleName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({SecurityConfig.class, RestAuthenticationEntryPoint.class, RestAccessDeniedHandler.class})
class AuthControllerTest {
    private static final String REGISTRATION = """
            {"name":"John","surname":"Smith","email":"user@example.com",
             "password":"safe-password","companyName":"Company"}
            """;

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AuthService authService;
    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    void registrationStillWorks() throws Exception {
        when(authService.registerApplicant(any())).thenReturn(
                new RegistrationResponse(1L, "John", "Smith", "user@example.com", null, RoleName.APPLICANT));

        mockMvc.perform(post("/api/v1/auth/register/applicant")
                        .contentType("application/json").content(REGISTRATION))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value("APPLICANT"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void employerRegistrationStillWorks() throws Exception {
        when(authService.registerEmployer(any())).thenReturn(
                new RegistrationResponse(2L, "John", "Smith", "user@example.com", "Company", RoleName.EMPLOYER));

        mockMvc.perform(post("/api/v1/auth/register/employer")
                        .contentType("application/json").content(REGISTRATION))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value("EMPLOYER"));
    }

    @Test
    void rejectsUnknownRoleField() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register/applicant")
                        .contentType("application/json")
                        .content(REGISTRATION.replace("}", ",\"role\":\"ADMIN\"}")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsInvalidEmailAndShortPassword() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login").contentType("application/json")
                        .content("{\"email\":\"invalid\",\"password\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalidLoginUsesGenericUnauthorizedResponse() throws Exception {
        when(authService.login(any())).thenThrow(new AuthUnauthorizedException());

        mockMvc.perform(post("/api/v1/auth/login").contentType("application/json")
                        .content("{\"email\":\"nobody@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid authentication credentials or token"));
    }

    @Test
    void occupiedEmailReturnsConflict() throws Exception {
        when(authService.registerApplicant(any())).thenThrow(new AlreadyExistsException("occupied"));
        mockMvc.perform(post("/api/v1/auth/register/applicant")
                        .contentType("application/json").content(REGISTRATION))
                .andExpect(status().isConflict());
    }
}
