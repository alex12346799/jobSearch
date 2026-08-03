package com.example.jobsearch.controller;

import com.example.jobsearch.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationService authService;
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("error", error != null);
        return "auth/login";
    }



    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "auth/forgot_password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        try {
            authService.sendResetPasswordLink(request);
            model.addAttribute("message", "Письмо для восстановления отправлено на вашу почту.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "auth/forgot_password";
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "auth/reset_password_form";
    }


    @PostMapping("/reset-password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        try {
            authService.sendResetPasswordLink(request);
            model.addAttribute("message", "Пароль успешно обновлён.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "message/message";
    }
}
