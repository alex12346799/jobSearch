package com.example.jobsearch.controller;

import com.example.jobsearch.model.Resume;
import com.example.jobsearch.service.impl.ResumeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resumes")
public class ResumeController {
    private final ResumeServiceImpl resumeService;

    public ResumeController(ResumeServiceImpl resumeService) {
        this.resumeService = resumeService;
    }
    @PostMapping
    public ResponseEntity<Resume> create(@RequestBody Resume resume) {
        Resume saved= resumeService.create(resume);
return ResponseEntity.ok(saved);
    }
    @GetMapping
    public List<Resume> findAll() {
        return resumeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resume> getById(@PathVariable int id) {
        Optional<Resume> found = resumeService.findById(id);
        return found.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
resumeService.delete(id);
return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Resume> update(@PathVariable int id, @RequestBody Resume resume) {
        Optional<Resume> updated = resumeService.update(id, resume);
        return updated.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/category/{categoryId}")
    public List<Resume> findByCategoryId(@PathVariable int categoryId) {
        return resumeService.findByVacancyId(categoryId);
    }
    @GetMapping("/applicant/{id}")
    public ResponseEntity<List<Resume>> getByApplicantId(@PathVariable int id) {
        return ResponseEntity.ok(resumeService.findByApplicantId(id));
    }

}
