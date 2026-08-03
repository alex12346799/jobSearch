package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.user.UserRegisterRequest;
import com.example.jobsearch.mapper.UserMapper;
import com.example.jobsearch.model.Role;
import com.example.jobsearch.model.User;
import com.example.jobsearch.repository.RoleRepository;
import com.example.jobsearch.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private AuthServiceImpl authService;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void employerRegistrationAssignsEmployerRole() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setEmail("employer@example.com");
        request.setPassword("password");
        Role employer = new Role();
        employer.setId(2L);
        employer.setName("EMPLOYER");
        User user = new User();
        user.setEmail(request.getEmail());

        when(roleRepository.findByName("EMPLOYER")).thenReturn(Optional.of(employer));
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(userMapper.fromRegisterDto(request)).thenReturn(user);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded-password");

        User registered = authService.registerEmployer(request, new MockHttpServletRequest());

        assertThat(registered.getRole()).isSameAs(employer);
        assertThat(registered.getRole().getName()).isEqualTo("EMPLOYER");
        verify(roleRepository).findByName("EMPLOYER");
        verify(userRepository).save(user);
    }
}
