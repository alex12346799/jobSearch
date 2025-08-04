package com.example.jobsearch.controller;

import com.example.jobsearch.dto1.ResumeCreateDto;
import com.example.jobsearch.mapper.ResumeMapper;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resumes")
@RequiredArgsConstructor
public class ResumeController {
    @Autowired
    private final ResumeService resumeService;

    @GetMapping
    public List<Resume> getAll() {
        return resumeService.getAllResumes();
    }

    @GetMapping("/{id}")
    public Resume getById(@PathVariable int id) {
        return resumeService.getById(id);
    }

    @GetMapping("/user/{applicantId}")
    public List<Resume> getByApplicant(@PathVariable int applicantId) {
        return resumeService.getAllByApplicantId(applicantId);
    }

    @PostMapping
    public void create (@RequestBody @Valid ResumeCreateDto resumeDto)
    {
        Resume resume = ResumeMapper.fromDto(resumeDto);
        resumeService.create(resume);
    }

    @PostMapping("/full")
    public ResponseEntity<String> createFullResume(@RequestBody Resume resume) {
        resumeService.createWithDetails(resume);
        return ResponseEntity.ok("Резюме с образованием и опытом успешно создано");
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateResume(@PathVariable int id, @RequestBody Resume resume) {
        resumeService.update(id, resume);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void deleteResume(@PathVariable int id) {
        resumeService.delete(id);
    }
}
