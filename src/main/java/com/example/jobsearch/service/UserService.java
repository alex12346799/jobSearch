package com.example.jobsearch.service;

import com.example.jobsearch.dto.UserEditRequestDto;
import com.example.jobsearch.dto.UserRequestDto;
import com.example.jobsearch.dto.UserRequestRegisterDto;
import com.example.jobsearch.dto.UserResponseDto;
import com.example.jobsearch.model.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService extends UserDetailsService {
    User getUserById(Long id);

   UserResponseDto getByEmail(String email);

    List<User> getAllUsers();


    User login(String email, String password);


    void updateUser(UserEditRequestDto dto, Authentication authentication);

    void uploadImageUser(MultipartFile file, String email);

    void deleteUser(long id);

    User register(UserRequestRegisterDto dto, HttpServletRequest request);

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String newPassword);

    void makeResetPasswordLink(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException;

 ResponseEntity<?> downloadImage(long imageId);
}
