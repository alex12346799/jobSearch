package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.UserEditRequestDto;
import com.example.jobsearch.dto.UserRequestRegisterDto;
import com.example.jobsearch.dto.UserResponseDto;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.mapper.UserMapper;
import com.example.jobsearch.model.Role;
import com.example.jobsearch.model.User;
import com.example.jobsearch.repository.RoleRepository;
import com.example.jobsearch.repository.UserRepository;
import com.example.jobsearch.service.ImageService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.utils.Utility;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final FileService fileService;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Пользователь с таким " + id + " не найден"));
    }

    @Override
    public UserResponseDto getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()
                -> new NotFoundException("Пользователь с таким " + email + " не найден"));
        return UserMapper.toDto(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


//    @Override
//    public User register(UserRequestRegisterDto dto, HttpServletRequest request) {
//
//        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
//            throw new NotFoundException("Пользователь с таким email уже занят");
//        }
//        Role role = roleRepository.findById(dto.getRoleId())
//                .orElseThrow(() -> new NotFoundException("Роль не найдена"));
//
//
//        User user = UserMapper.fromDtoRegister(dto);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRole(role);
//
//        user.setEnabled(true);
//
//        User registeredUser = userRepository.save(user);
//
//
//        UserDetails userDetails = loadUserByUsername(user.getEmail());
//        Authentication auth = new UsernamePasswordAuthenticationToken(
//                userDetails,
//                userDetails.getPassword(),
//                userDetails.getAuthorities()
//        );
//        SecurityContextHolder.getContext().setAuthentication(auth);
//        request.getSession(true).setAttribute(
//                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
//                SecurityContextHolder.getContext()
//        );
//        return registeredUser;
//
//    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователя с таким email не найден"));


        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new NotFoundException("Неверный пароль");
        }
        return user;
    }

    @Override
    public void updateUser(UserEditRequestDto dto, Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким email не найден"));

        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setAge(dto.getAge());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        userRepository.save(user);
    }


    @Override
    public void uploadImageUser(MultipartFile file, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException("Пользователь не найден"));
        if (!file.isEmpty()) {
            String fileName = imageService.saveUploadedFile(file, "image");
            user.setAvatar(fileName);
        }
        userRepository.save(user);
    }


    @Override
    public void deleteUser(long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities()
        );

    }

    private void updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        user.setResetPasswordToken(token);
        userRepository.saveAndFlush(user);
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void makeResetPasswordLink(HttpServletRequest request) throws UsernameNotFoundException, MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();
        updateResetPasswordToken(token, email);

        String url = Utility.makeSiteUrl(request) + "/auth/reset-password?token=" + token;
        emailService.sendEmail(email, url);
    }

    private Boolean isAuthor(Authentication authentication, String author) {
        return true;
    }

    @Override
    public ResponseEntity<?> downloadImage(long imageId) {
        User user = getUserById(imageId);
        String filename = user.getAvatar();
        if (filename == null || filename.isEmpty()) {
            filename = "picture.png";
        }
        return fileService.getOutputFile(filename, MediaType.IMAGE_PNG);
    }

    @Override
    public User register(UserRequestRegisterDto dto, Role role, HttpServletRequest request) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new NotFoundException("Пользователь с таким email уже существует");
        }
        User user = UserMapper.fromDtoRegister(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);
        user.setEnabled(true);
        User savedUser = userRepository.save(user);
        UserDetails userDetails = loadUserByUsername(user.getEmail());
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        request.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        return savedUser;
    }


    @Override
    public User registerApplicant(UserRequestRegisterDto dto, HttpServletRequest request) {
        Role role = roleRepository.findByName("APPLICANT")
                .orElseThrow(() -> new NotFoundException("Роль не найдена"));
        return register(dto, role, request);
    }

    @Override
    public User registerEmployer(UserRequestRegisterDto dto, HttpServletRequest request) {
        Role role = roleRepository.findByName("EMPLOYEE")
                .orElseThrow(() -> new NotFoundException("Роль не найдена"));
        return register(dto, role, request);
    }

}
