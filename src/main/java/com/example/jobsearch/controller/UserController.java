package com.example.jobsearch.controller;

import com.example.jobsearch.dto.UserRequestDto;
import com.example.jobsearch.dto.UserResponseDto;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.User;
import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.repository.VacancyRepository;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller("UserWebController")
@RequestMapping("auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;

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

    //    @GetMapping("/profile")
//    public String infoUser(Model model, Authentication authentication) {
//        if (authentication == null) {
//            return "redirect:/login";
//        }
//        String email = authentication.getName();
//        UserResponseDto user = userService.getByEmail(email);
//        model.addAttribute("user", user);
//        List<Resume> resumes = resumeService.findByApplicantId(authentication);
//        model.addAttribute("resumes", resumes);
//        return "profile/profile";
//    }
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
                        .getAuthority().equals("ROLE_APPLICANT"));
        boolean isEmployer = authentication.getAuthorities().stream()
                .anyMatch(e -> e
                        .getAuthority().equals("ROLE_EMPLOYEE"));
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

    @PostMapping("/upload-avatar")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile file, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        String email = authentication.getName();
        userService.uploadImageUser(file, email);
        return "redirect:/auth/profile";
    }

}
