package com.example.jobsearch.controller;

import com.example.jobsearch.dto.UserEditRequestDto;
import com.example.jobsearch.dto.UserRequestDto;
import com.example.jobsearch.dto.UserResponseDto;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.User;
import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.repository.UserRepository;
import com.example.jobsearch.repository.VacancyRepository;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import com.example.jobsearch.utils.RedirectHelper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Controller("UserWebController")
@RequestMapping("auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final RedirectHelper redirectHelper;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRequestDto", new UserRequestDto());
        return "register/register";
    }

    @PostMapping("/register")
    public void registerUser(UserRequestDto dto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User registeredUser = userService.register(dto, request);
        redirectHelper.redirectByRole(registeredUser.getAuthorities(), response);

    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/update")
    public String updateUser(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        String email = authentication.getName();
        UserResponseDto user = userService.getByEmail(email);
        model.addAttribute("user", user);
        return "editUser/editingUser";
    }

    @PostMapping("/update")
    public String updateUser(@Valid UserEditRequestDto dto, BindingResult bindingResult, Authentication auth, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("userRequestDto", dto);
            return "redirect:/auth/update";
        }
        userService.updateUser(dto, auth);
        return  "redirect:/auth/profile";
    }

    @GetMapping("/profile")
    public String infoUser(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        String email = authentication.getName();
        UserResponseDto user = userService.getByEmail(email);
        model.addAttribute("user", user);
        boolean isApplicant = authentication.getAuthorities().stream()
                .anyMatch(a -> a
                        .getAuthority().equals("APPLICANT"));
        boolean isEmployer = authentication.getAuthorities().stream()
                .anyMatch(e -> e
                        .getAuthority().equals("EMPLOYEE"));
        model.addAttribute("isApplicant", isApplicant);
        model.addAttribute("isEmployer", isEmployer);
        if (isApplicant) {
            List<Resume> resumes = resumeService.findByApplicantId(authentication);
            model.addAttribute("resumes", resumes);
        } else if (isEmployer) {
            List<Vacancy> vacancies = vacancyService.findByEmployer(authentication);
            model.addAttribute("vacancies", vacancies);
        } else {
            return "redirect:/login";
        }
        return "profile/profile";
    }


    @PostMapping("/upload-avatar")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile file, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        String email = authentication.getName();
        userService.uploadImageUser(file, email);
        return "redirect:/auth/profile";
    }

    @GetMapping("forgot-password")
    public String showForgotPasswordPage(Model model, Authentication authentication) {
        return "auth/forgot_password";
    }

    @PostMapping("forgot-password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        try {
            userService.makeResetPasswordLink(request);
            model.addAttribute("message", "we have sent a reset password link to your email. Please check it.");
        } catch (NotFoundException | UnsupportedEncodingException e) {
            model.addAttribute("error", e.getMessage());
        } catch (MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }
        return "auth/forgot_password";
    }

    @GetMapping("reset-password")
    public String resetPassword(@RequestParam(name = "token") String token, Model model) {
        try {
            userService.getByResetPasswordToken(token);
            model.addAttribute("token", token);
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", "invalid token");
        }
        return "auth/reset_password_form";
    }

    @PostMapping("reset-password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        try {
            User user = userService.getByResetPasswordToken(token);
            userService.updatePassword(user, password);
            model.addAttribute("message", "You have successfully updated your password.");
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", "invalid token");
        }
        return "message/message";
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<?> downloadImage(@PathVariable("imageId") long imageId) {
        return userService.downloadImage(imageId);
    }

}
