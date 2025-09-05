//package com.example.jobsearch.controller;
//
//import com.example.jobsearch.dto.UserRequestDto;
//import com.example.jobsearch.model.User;
//import com.example.jobsearch.service.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@RequiredArgsConstructor
//public class AuthController {
//    private final UserService userService;
//    private final UserDetailsService userDetailsService;
//
//    @GetMapping("login")
//    public String login(Model model, @RequestParam(defaultValue = "false") Boolean error) {
//        model.addAttribute("error", error);
//        return "auth/login";
//    }
//
//    @GetMapping("/register")
//    public String register(Model model) {
//        model.addAttribute("user", new UserRequestDto());
//        return "auth/register";
//    }
//
//    @PostMapping("/register")
//    public String registerUser(@Valid UserRequestDto dto, HttpServletRequest request, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("user", new UserRequestDto());
//            return "auth/register";
//        }
//
//        userService.register(dto, request);
//
//        return "redirect:/";
//    }
//}
