package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.user.RegistrationRequest;
import com.example.jobsearch.dto.user.RegistrationResponse;
import com.example.jobsearch.exceptions.AlreadyExistsException;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.exceptions.SystemRoleMissingException;
import com.example.jobsearch.model.Role;
import com.example.jobsearch.model.RoleName;
import com.example.jobsearch.model.User;
import com.example.jobsearch.repository.RoleRepository;
import com.example.jobsearch.repository.UserRepository;
import com.example.jobsearch.service.RegistrationService;
import com.example.jobsearch.utils.Utility;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements RegistrationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public RegistrationResponse registerApplicant(RegistrationRequest request) {
        return register(request, RoleName.APPLICANT);
    }

    @Override
    @Transactional
    public RegistrationResponse registerEmployer(RegistrationRequest request) {
        return register(request, RoleName.EMPLOYER);
    }

    private RegistrationResponse register(RegistrationRequest request, RoleName roleName) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new AlreadyExistsException("A user with this email already exists");
        }
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(SystemRoleMissingException::new);
        User user = new User();
        user.setName(request.name().trim());
        user.setSurname(request.surname().trim());
        user.setEmail(email);
        user.setCompanyName(trimToNull(request.companyName()));
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(role);
        user.setEnabled(true);
        User saved = userRepository.save(user);
        return new RegistrationResponse(
                saved.getId(), saved.getName(), saved.getSurname(), saved.getEmail(),
                saved.getCompanyName(), saved.getRole().getName()
        );
    }

    private String trimToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }


    @Override
    public void sendResetPasswordLink(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);
        userRepository.save(user);

        String resetLink = Utility.makeSiteUrl(request) + "/auth/reset-password?token=" + token;
        emailService.sendEmail(email, resetLink);
    }

    @Override
    public void updatePassword(String token, String newPassword) {
        User user = userRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new NotFoundException("Неверный токен сброса пароля"));
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}
