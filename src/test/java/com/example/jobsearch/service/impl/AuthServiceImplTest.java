package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.user.RegistrationRequest;
import com.example.jobsearch.dto.user.RegistrationResponse;
import com.example.jobsearch.exceptions.AlreadyExistsException;
import com.example.jobsearch.model.Role;
import com.example.jobsearch.model.RoleName;
import com.example.jobsearch.model.User;
import com.example.jobsearch.repository.RoleRepository;
import com.example.jobsearch.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void registersApplicantWithNormalizedEmailAndEncodedPassword() {
        Role applicant = role(RoleName.APPLICANT);
        when(roleRepository.findByName(RoleName.APPLICANT)).thenReturn(Optional.of(applicant));
        when(passwordEncoder.encode("safe-password")).thenReturn("encoded-password");
        when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class)))
                .thenAnswer(invocation -> withId(invocation.getArgument(0), 10L));

        RegistrationResponse response = authService.registerApplicant(request(" User@Example.COM "));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).existsByEmailIgnoreCase("user@example.com");
        verify(roleRepository).findByName(RoleName.APPLICANT);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo("user@example.com");
        assertThat(captor.getValue().getPassword()).isEqualTo("encoded-password");
        assertThat(response.role()).isEqualTo(RoleName.APPLICANT);
    }

    @Test
    void registersEmployerUsingRoleNameInsteadOfId() {
        Role employer = role(RoleName.EMPLOYER);
        when(roleRepository.findByName(RoleName.EMPLOYER)).thenReturn(Optional.of(employer));
        when(passwordEncoder.encode("safe-password")).thenReturn("encoded-password");
        when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class)))
                .thenAnswer(invocation -> withId(invocation.getArgument(0), 11L));

        RegistrationResponse response = authService.registerEmployer(request("employer@example.com"));

        assertThat(response.role()).isEqualTo(RoleName.EMPLOYER);
        verify(roleRepository).findByName(RoleName.EMPLOYER);
    }

    @Test
    void rejectsOccupiedNormalizedEmail() {
        when(userRepository.existsByEmailIgnoreCase("user@example.com")).thenReturn(true);

        assertThatThrownBy(() -> authService.registerApplicant(request(" User@Example.com ")))
                .isInstanceOf(AlreadyExistsException.class);
    }

    private RegistrationRequest request(String email) {
        return new RegistrationRequest(" John ", " Smith ", email, "safe-password", " Company ");
    }

    private Role role(RoleName name) {
        Role role = new Role();
        role.setName(name);
        return role;
    }

    private User withId(User user, Long id) {
        user.setId(id);
        return user;
    }
}
