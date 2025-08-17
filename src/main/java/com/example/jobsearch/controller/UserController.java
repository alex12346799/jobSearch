package com.example.jobsearch.controller;

import com.example.jobsearch.dto.UserRequestDto;
import com.example.jobsearch.dto.UserResponseDto;
import com.example.jobsearch.model.User;
import com.example.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller("UserWebController")
@RequestMapping("auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRequestDto", new UserRequestDto());
        return "register/register";
    }
    @PostMapping("/register")
    public String registerUser(UserRequestDto dto) {
        userService.register(dto);
        return "redirect:/";
    }
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    @PostMapping("/login")
    public String loginUser(UserRequestDto dto) {
        userService.login(dto.getEmail(), dto.getPassword());
        return "redirect:/auth/profile";
    }

    @GetMapping("/update")
    public String updateUser(UserRequestDto dto, Model model) {
        userService.updateUser(dto);
        model.addAttribute("userRequestDto", dto);
        return "editUser/editingUser";
    }
    @GetMapping("/profile")
    public String infoUser(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        String email = authentication.getName();
        UserResponseDto user = userService.getByEmail(email);
        model.addAttribute("user", user);
        return "profile/profile";
    }

    @GetMapping("/profileAdmin")
    public String profileAdmin(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        String email = authentication.getName();
        UserResponseDto user = userService.getByEmail(email);
        model.addAttribute("user", user);
        return "profileAdmin/profileAdmin";
    }
}
