package com.example.jobsearch.controller;

import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.dto.VacancyRequestDto;
import com.example.jobsearch.dto.VacancyResponseDto;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.model.Category;
import com.example.jobsearch.model.User;
import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.repository.UserRepository;
import com.example.jobsearch.repository.VacancyRepository;
import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("vacancyWebController")
@RequiredArgsConstructor
@RequestMapping("vacancies")
public class VacancyController {
    private final VacancyService vacancyService;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final VacancyRepository vacancyRepository;


    @GetMapping
    public String showVacancies(@RequestParam(required = false, defaultValue = "") String filter, Model model,
                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Vacancy> page;
        page = vacancyRepository.findAll(pageable);

        model.addAttribute("page", page);
        model.addAttribute("vacancy", page.getContent());
        model.addAttribute("url", "/vacancies");
        model.addAttribute("categories", categoryService.findAll());

        return "vacancies/vacancies";
    }


    @GetMapping("sorted")
    public String vacancySorted (Pageable pageable, Model model){
    model.addAttribute("vacancy", vacancyService.getAllSortedAndPagedVacancy(pageable));
    return "vacancies/vacancies";
    }


    @GetMapping("/create")
    public String createVacancy(Model model, Authentication auth) {
        if (auth == null) {
            return "redirect:/login";
        }
        String username = auth.getName();
        User user = userRepository.findByEmail(username).orElseThrow(()
                -> new NotFoundException("Пользователь не найден"));

        model.addAttribute("employerId", user.getId());
        model.addAttribute("vacancyRequestDto", new VacancyRequestDto());
        model.addAttribute("categories", categoryService.findAll());
        List<VacancyResponseDto> vacancy = vacancyService.getAll();
        return "vacancies/createVacancies";
    }

@PostMapping("/create")
public String createVacancy(
        @Valid VacancyRequestDto vacancyRequestDto,
        BindingResult bindingResult,
        Model model,
        Authentication auth) {

    if (bindingResult.hasErrors()) {
        // обязательно возвращаем категории, иначе выпадающий список не покажется
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("vacancyRequestDto", vacancyRequestDto);
        return "vacancies/createVacancies";
    }

    Vacancy vacancy = vacancyService.create(vacancyRequestDto, auth);
    model.addAttribute("vacancy", vacancy);
    return "redirect:/auth/profile";
}
    @GetMapping("{vacanciesId}")
    public String getVacanciesId(@PathVariable long vacanciesId, Model model) {
        model.addAttribute("vacanciesRequest", vacancyService.getById(vacanciesId));
        return "vacancies/vacanciesDetails";
    }
}
