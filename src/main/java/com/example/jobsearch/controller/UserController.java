
package com.example.jobsearch.controller;

import com.example.jobsearch.dto.user.UserEditRequest;
import com.example.jobsearch.service.FileService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FileService fileService;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;

    @GetMapping("/profile")
    public String profilePage(Authentication authentication, Model model) {
        // заново загружаем юзера из БД, не кэшируем старый объект
        var user = userService.getByEmail(authentication.getName());
        model.addAttribute("user", user);

        boolean isApplicant = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("APPLICANT"));
        boolean isEmployer = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("EMPLOYEE"));

        model.addAttribute("isApplicant", isApplicant);
        model.addAttribute("isEmployer", isEmployer);

        if (isApplicant) {
            var resumes = resumeService.findByApplicantId(authentication);
            model.addAttribute("resumes", resumes);
        }
        if (isEmployer) {
            var vacancies = vacancyService.findByEmployer(authentication);
            model.addAttribute("vacancies", vacancies);
        }

        return "profile/profile";
    }

    @GetMapping("/edit")
    public String editPage(Authentication authentication, Model model) {
        model.addAttribute("userEditRequest", new UserEditRequest());
        model.addAttribute("user", userService.getCurrentUser(authentication));
        return "editUser/editingUser";
    }

    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute("userEditRequest") UserEditRequest dto,
                                BindingResult result,
                                Authentication authentication,
                                Model model) {

        if (result.hasErrors()) {
            model.addAttribute("user", userService.getCurrentUser(authentication));
            return "profile/profile";
        }

        userService.updateUser(dto, authentication);
        return "redirect:/user/profile";
    }

    @PostMapping("/upload-avatar")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile file,
                               Authentication authentication) {
        String email = authentication.getName();
        fileService.uploadUserAvatar(file, email);
        return "redirect:/user/profile";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
