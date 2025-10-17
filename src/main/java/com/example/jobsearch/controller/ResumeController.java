package com.example.jobsearch.controller;

import com.example.jobsearch.dto.ResumeRequestDto;
import com.example.jobsearch.dto.ResumeResponseDto;
import com.example.jobsearch.dto.WorkExperienceInfoRequestDto;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.model.Category;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.User;
import com.example.jobsearch.model.WorkExperienceInfo;
import com.example.jobsearch.repository.CategoryRepository;
import com.example.jobsearch.repository.ResumeRepository;
import com.example.jobsearch.repository.UserRepository;
import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.ResumeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller("resumeWebController")
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final ResumeRepository resumeRepository;


    @GetMapping()
    public String showAllResumes(@RequestParam(required = false, defaultValue = "") String filter,
                                 Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Resume> page;

        page = resumeRepository.findAll(pageable);

        model.addAttribute("page", page);
        model.addAttribute("url", "/resumes");

        return "resumes/resumes";
    }

    @GetMapping("{resumeId}")
    public String getResumeId(@PathVariable long resumeId, Model model) {
        model.addAttribute("resume", resumeService.getById(resumeId));
        return "resumes/resumeDetails";
    }

    @GetMapping("sorted")
    public String getResumesSorted(
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(name = "page", defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Sort.Direction dir = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(dir, sort));

        Page<Resume> resumes = resumeRepository.findAll(pageable);


        if (pageNum >= resumes.getTotalPages() && resumes.getTotalPages() > 0) {
            return "redirect:/resumes/sorted?page=0&size=" + size + "&sort=" + sort + "&direction=" + direction;
        }


        model.addAttribute("page", resumes);


        model.addAttribute("url", "/resumes/sorted");
        model.addAttribute("extraParams",


                "sort=" + sort + "&direction=" + direction);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDirection", direction);

        return "resumes/resumes";
    }


    @GetMapping("/create")
    public String createResume
            (Model model, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(()
                        -> new NotFoundException("Пользователь не найден"));

        model.addAttribute("applicantId", user.getId());
        model.addAttribute("categories", categoryService.findAll());
        return "resumes/createResume";
    }


    @PostMapping("/create")
    public String createResume(ResumeRequestDto resumeRequestDto,
                               HttpServletRequest request,
                               Model model,
                               Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));


        resumeRequestDto.setIsActive(request.getParameter("resumeRequestDto.isActive").equals("on"));

        log.error("Object of resumeRequestDto: " + resumeRequestDto);
        System.out.println("ОПЫТ РАБОТЫ (size) " + resumeRequestDto.getWorkExperienceInfoList().size());
        Resume createResume = resumeService.create(resumeRequestDto, authentication);
        model.addAttribute("resume", createResume);
        return "redirect:/user/profile";
    }


}
