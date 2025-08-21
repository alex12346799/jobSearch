package com.example.jobsearch.controller;

import com.example.jobsearch.dto.UserRequestDto;
import com.example.jobsearch.model.User;
import com.example.jobsearch.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
@GetMapping("login")
    public String login() {
    return "auth/login";
}

    @GetMapping("/register")
    public String register(Model model) {
    model.addAttribute("user", new UserRequestDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid UserRequestDto dto, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
        model.addAttribute("user", new UserRequestDto());
        return "auth/register";
    }
        userService.register(dto);

        return "redirect:/";
    }
}
