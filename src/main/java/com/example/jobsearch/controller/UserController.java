package com.example.jobsearch.controller;

import com.example.jobsearch.dto.UserRequestDto;
import com.example.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
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
        return "redirect:/auth/login";
    }
    @GetMapping("/dasfdsf")
    public String updateUser(UserRequestDto dto, Model model) {
        userService.updateUser(dto);
        model.addAttribute("userRequestDto", dto);
        return "editUser/editingUser";
    }
    @GetMapping("/qwe")
    public String infoUser(UserRequestDto dto, Model model) {
        userService.updateUser(dto);
        model.addAttribute("userRequestDto", dto);
        return "infoUser/infoUser";
    }
}
