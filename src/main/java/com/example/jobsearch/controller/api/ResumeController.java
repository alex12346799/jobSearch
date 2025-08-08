package com.example.jobsearch.controller.api;

import com.example.jobsearch.dto.ResumeRequestDto;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController("restProfile")
@RequestMapping("api//resumes")
@RequiredArgsConstructor
public class ResumeController {
    @Autowired
    private final ResumeService resumeService;

//    @GetMapping
//    public List<Resume> getAll() {
//        return resumeService.getAllResumes();
//    }

    @GetMapping("/{id}")
    public Resume getById(@PathVariable int id) {
        return resumeService.getById(id);
    }

    @GetMapping("/user/{applicantId}")
    public List<Resume> getByApplicant(@PathVariable int applicantId) {
        return resumeService.getAllByApplicantId(applicantId);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid ResumeRequestDto resumeDto) {

        resumeService.create(resumeDto);
        return ResponseEntity.ok("Резюме создана");
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateResume(@PathVariable int id, @RequestBody Resume resume, Authentication auth) {
        resume.setId(id);
        resumeService.update(resume, id, auth);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void deleteResume(@PathVariable int id,  Authentication auth) {
        resumeService.delete(id, auth);
    }
}
