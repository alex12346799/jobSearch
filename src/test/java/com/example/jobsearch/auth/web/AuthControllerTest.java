package com.example.jobsearch.auth.web;

import com.example.jobsearch.auth.application.AuthService;
import com.example.jobsearch.auth.application.AuthUnauthorizedException;
import com.example.jobsearch.auth.application.PasswordResetValidationException;
import com.example.jobsearch.auth.security.RestAccessDeniedHandler;
import com.example.jobsearch.auth.security.RestAuthenticationEntryPoint;
import com.example.jobsearch.config.SecurityConfig;
import com.example.jobsearch.exceptions.AlreadyExistsException;
import com.example.jobsearch.user.domain.RoleName;
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

    @Test
    void forgotPasswordAlwaysReturnsSameAcceptedResponse() throws Exception {
        ForgotPasswordResponse response = new ForgotPasswordResponse(
                "If the account is eligible, password reset instructions will be sent");
        when(authService.forgotPassword(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/password/forgot").contentType("application/json")
                        .content("{\"email\":\" User@Example.COM \"}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value(response.message()));
        mockMvc.perform(post("/api/v1/auth/password/forgot").contentType("application/json")
                        .content("{\"email\":\"missing@example.com\"}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value(response.message()));
    }

    @Test
    void passwordResetValidationAndUnknownFieldsReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/auth/password/reset").contentType("application/json")
                        .content("{\"token\":\"token\",\"newPassword\":\"short\",\"confirmPassword\":\"short\"}"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/v1/auth/password/reset").contentType("application/json")
                        .content("{\"token\":\"token\",\"newPassword\":\"new-password\","
                                + "\"confirmPassword\":\"new-password\",\"role\":\"ADMIN\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void mismatchedPasswordConfirmationReturnsBadRequest() throws Exception {
        org.mockito.Mockito.doThrow(new PasswordResetValidationException("Password confirmation does not match"))
                .when(authService).resetPassword(any());
        mockMvc.perform(post("/api/v1/auth/password/reset").contentType("application/json")
                        .content("{\"token\":\"token\",\"newPassword\":\"new-password\","
                                + "\"confirmPassword\":\"other-password\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Password confirmation does not match"));
    }
}
