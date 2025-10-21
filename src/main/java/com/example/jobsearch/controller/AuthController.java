package com.example.jobsearch.controller;

import com.example.jobsearch.dto.user.UserRegisterRequest;
import com.example.jobsearch.exceptions.AlreadyExistsException;
import com.example.jobsearch.service.RegistrationService;
import com.example.jobsearch.validation.ApplicantGroup;
import com.example.jobsearch.validation.EmployerGroup;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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



    @GetMapping("/register")
    public String showRoleSelectionPage() {
        return "auth/role-selection";
    }

    @GetMapping("/register/applicant")
    public String showApplicantForm(Model model) {
        model.addAttribute("userRegisterRequest", new UserRegisterRequest());
        return "auth/register-applicant";
    }

    @PostMapping("/register/applicant")
    public String registerApplicant(
            @Validated(ApplicantGroup.class) @ModelAttribute("userRegisterRequest") UserRegisterRequest dto,
            BindingResult bindingResult,
            HttpServletRequest request,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/register-applicant";
        }
  try {
      var registeredUser = authService.registerApplicant(dto, request);
      return "redirect:/user/profile";
  }catch  (AlreadyExistsException ex){
      model.addAttribute("errorMessage", ex.getMessage());
      return "auth/register-applicant";
        }

    }

    @GetMapping("/register/employer")
    public String showEmployerForm(Model model) {
        model.addAttribute("userRegisterRequest", new UserRegisterRequest());
        return "auth/register-employer";
    }

    @PostMapping("/register/employer")
    public String registerEmployer(
            @Validated(EmployerGroup.class) @ModelAttribute("userRegisterRequest") UserRegisterRequest dto,
            BindingResult bindingResult,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "auth/register-employer";
        }
        var registeredUser = authService.registerEmployer(dto, request);
        return "redirect:/user/profile";
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

