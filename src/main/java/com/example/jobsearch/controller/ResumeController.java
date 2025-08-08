package com.example.jobsearch.controller;

import com.example.jobsearch.dto.ResumeResponseDto;
import com.example.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("resumeWebController")
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @GetMapping()
    public String showAllResumes(Model model) {
        List<ResumeResponseDto> resumes = resumeService.getAllResumes();
        model.addAttribute("resumes", resumes);

        return "resumes/resumes";
    }
}
