package com.example.jobsearch.controller.api;

import com.example.jobsearch.config.SecurityConfig;
import com.example.jobsearch.dto.user.RegistrationRequest;
import com.example.jobsearch.dto.user.RegistrationResponse;
import com.example.jobsearch.exceptions.AlreadyExistsException;
import com.example.jobsearch.model.RoleName;
import com.example.jobsearch.security.CustomAuthenticationSuccessHandler;
import com.example.jobsearch.security.CustomUserDetailsService;
import com.example.jobsearch.service.RegistrationService;
import com.example.jobsearch.utils.RedirectHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthRestController.class)
@Import(SecurityConfig.class)
class AuthRestControllerTest {
    private static final String VALID_REQUEST = """
            {"name":"John","surname":"Smith","email":"user@example.com",
             "password":"safe-password","companyName":"Company"}
            """;

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private RegistrationService registrationService;
    @MockitoBean
    private RedirectHelper redirectHelper;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;
    @MockitoBean
    private PasswordEncoder passwordEncoder;
    @MockitoBean
    private CustomAuthenticationSuccessHandler successHandler;

    @Test
    void registersApplicant() throws Exception {
        when(registrationService.registerApplicant(any())).thenReturn(response(RoleName.APPLICANT));

        mockMvc.perform(post("/api/v1/auth/register/applicant")
                        .contentType("application/json").content(VALID_REQUEST))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value("APPLICANT"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void registersEmployer() throws Exception {
        when(registrationService.registerEmployer(any())).thenReturn(response(RoleName.EMPLOYER));

        mockMvc.perform(post("/api/v1/auth/register/employer")
                        .contentType("application/json").content(VALID_REQUEST))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value("EMPLOYER"));
    }

    @Test
    void rejectsInvalidEmail() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register/applicant")
                        .contentType("application/json").content(VALID_REQUEST.replace("user@example.com", "invalid")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.email").exists());
    }

    @Test
    void rejectsShortPassword() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register/applicant")
                        .contentType("application/json").content(VALID_REQUEST.replace("safe-password", "short")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.password").exists());
    }

    @Test
    void returnsConflictForOccupiedEmail() throws Exception {
        when(registrationService.registerApplicant(any())).thenThrow(new AlreadyExistsException("occupied"));

        mockMvc.perform(post("/api/v1/auth/register/applicant")
                        .contentType("application/json").content(VALID_REQUEST))
                .andExpect(status().isConflict());
    }

    @Test
    void requestCannotSelectAdminRole() throws Exception {
        when(registrationService.registerApplicant(any())).thenReturn(response(RoleName.APPLICANT));

        mockMvc.perform(post("/api/v1/auth/register/applicant")
                        .contentType("application/json")
                        .content(VALID_REQUEST.replace("}", ",\"role\":\"ADMIN\"}")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value("APPLICANT"));

        verify(registrationService).registerApplicant(any(RegistrationRequest.class));
    }

    private RegistrationResponse response(RoleName role) {
        return new RegistrationResponse(1L, "John", "Smith", "user@example.com", "Company", role);
    }
}
