package com.example.jobsearch.controller.api;

import com.example.jobsearch.dto.user.RegistrationRequest;
import com.example.jobsearch.dto.user.RegistrationResponse;
import com.example.jobsearch.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final RegistrationService registrationService;

    @PostMapping("/register/applicant")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponse registerApplicant(@Valid @RequestBody RegistrationRequest request) {
        return registrationService.registerApplicant(request);
    }

    @PostMapping("/register/employer")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponse registerEmployer(@Valid @RequestBody RegistrationRequest request) {
        return registrationService.registerEmployer(request);
    }
}
