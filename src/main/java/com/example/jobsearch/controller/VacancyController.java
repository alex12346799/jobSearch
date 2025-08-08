package com.example.jobsearch.controller;

import com.example.jobsearch.dto.VacancyResponseDto;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("vacancyWebController")
@RequiredArgsConstructor
@RequestMapping("vacancies")
public class VacancyController {
    private final VacancyService vacancyService;
    @GetMapping
    public  String showVacancies(Model model) {
        List<VacancyResponseDto> vacancy = vacancyService.getAll();
        model.addAttribute("vacancy", vacancy);
        return "vacancies/vacancies";
    }
}
