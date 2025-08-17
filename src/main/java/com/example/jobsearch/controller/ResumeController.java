package com.example.jobsearch.controller;
import com.example.jobsearch.dto.ResumeRequestDto;
import com.example.jobsearch.dto.ResumeResponseDto;
import com.example.jobsearch.dto.WorkExperienceInfoRequestDto;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.model.Category;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.User;
import com.example.jobsearch.repository.CategoryRepository;
import com.example.jobsearch.repository.UserRepository;
import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("resumeWebController")
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
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

    @GetMapping("sorted")
    public String getResumesSorted(
//            @RequestParam(name = "sort", required = false, defaultValue = "name") String sortValue,
//            @RequestParam(name = "page", required = false) int page,
//            @RequestParam(name = "size", required = false) int size,
            Pageable pageable,
         Model model) {
        model.addAttribute("resumes", resumeService.getAllSortedAndPagedResume(pageable));
        return "resumes/resumes";
    }


//    @GetMapping("/create")
//    public String createResume(Model model, Authentication authentication) {
//        String username = authentication.getName();
//        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
//
//
//
//        model.addAttribute("applicantId", user.getId());
//        model.addAttribute("resumeRequestDto", new ResumeRequestDto());
//        List<Category> categories = categoryService.findAll();
//        model.addAttribute("categories", categories);
//        return "resumes/createResume";
//    }
@GetMapping("/create")
public String createResume(Model model, Authentication auth) {
    User user = userRepository.findByEmail(auth.getName())
            .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    model.addAttribute("applicantId", user.getId());
    WorkExperienceInfoRequestDto workExperienceDto = new WorkExperienceInfoRequestDto();
    model.addAttribute("workExperienceDto", workExperienceDto);
    model.addAttribute("categories", categoryService.findAll());
    return "resumes/createResume";
}


    @PostMapping("/create")
    public String createResume(@Valid ResumeRequestDto dto, BindingResult bindingResult, Model model, Authentication authentication) {
      if (bindingResult.hasErrors()) {
          model.addAttribute("resumeRequestDto", dto);
          return "resumes/createResume";
      }
        Resume createResume = resumeService.create(dto, authentication);
        model.addAttribute("resume", createResume);
        return "redirect:/" + createResume.getId();
    }
}
