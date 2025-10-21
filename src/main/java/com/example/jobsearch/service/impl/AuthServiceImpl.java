package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.user.UserRegisterRequest;
import com.example.jobsearch.exceptions.AlreadyExistsException;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.mapper.UserMapper;
import com.example.jobsearch.model.Role;
import com.example.jobsearch.model.User;
import com.example.jobsearch.repository.RoleRepository;
import com.example.jobsearch.repository.UserRepository;
import com.example.jobsearch.service.RegistrationService;
import com.example.jobsearch.utils.Utility;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements RegistrationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    @Override
    public User registerApplicant(UserRegisterRequest dto, HttpServletRequest request) {
        Role role = roleRepository.findByName("APPLICANT")
                .orElseThrow(() -> new NotFoundException("Роль APPLICANT не найдена"));
        return register(dto, role, request);
    }

    @Override
    public User registerEmployer(UserRegisterRequest dto, HttpServletRequest request) {
        Role role = roleRepository.findByName("EMPLOYEE")
                .orElseThrow(() -> new NotFoundException("Роль EMPLOYEE не найдена"));
        return register(dto, role, request);
    }

    public User register(UserRegisterRequest dto, Role role, HttpServletRequest request){
        userRepository.findByEmail(dto.getEmail()).ifPresent(u -> {
            throw new AlreadyExistsException("Пользователь с таким email уже существует");
        });
        User user = userMapper.fromRegisterDto(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role);
        user.setEnabled(true);
        userRepository.save(user);
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().getName())
                .build();

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        request.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        return user;
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
