package com.example.jobsearch.controller;

import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.dto.ResumeRequestDto;
import com.example.jobsearch.dto.ResumeResponseDto;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.User;
import com.example.jobsearch.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("resumeWebController")
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final UserDao userDao;

    @GetMapping()
    public String showAllResumes(Model model) {
        List<ResumeResponseDto> resumes = resumeService.getAllResumes();
        model.addAttribute("resumes", resumes);

        return "resumes/resumes";
    }

    @GetMapping("{resumeId}")
    public String getResumeId(@PathVariable long resumeId, Model model) {
        model.addAttribute("resume", resumeService.getById(resumeId));
        return "resumes/view";
    }

    //    @GetMapping("/create")
//    public String createResume(Model model, Authentication authentication) {
//        String username = authentication.getName();
//        User user = userDao.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
//        model.addAttribute("applicantId", user.getId());
//        model.addAttribute("resumeRequestDto", new ResumeRequestDto());
//        return "resumes/createResume";
//    }
    @GetMapping("/create")
    public String createResume(@Valid Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userDao.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        model.addAttribute("applicantId", user.getId());
        model.addAttribute("resumeRequestDto", new ResumeRequestDto());
        return "resumes/createResume";
    }

    @PostMapping("/create")
    public String createResume(@Valid ResumeRequestDto dto, Model model) {
        dto.toString();
        Resume createResume = resumeService.create(dto);
        model.addAttribute("resume", createResume);
        return "redirect:/git" + createResume.getId();
    }
}
