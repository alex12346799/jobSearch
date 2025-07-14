package com.example.jobsearch.controller;

import com.example.jobsearch.model.Resume;
import com.example.jobsearch.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resumes")
public class ResumeController {
    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
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
    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable int id) {
resumeService.delete(id);
return ResponseEntity.ok().build();
    }
    @PutMapping
    public ResponseEntity<Resume> update(@PathVariable int id, @RequestBody Resume resume) {
        Optional<Resume> updated = resumeService.update(id, resume);
        return updated.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/category/{categoryId}")
    public List<Resume> findByCategoryId(@PathVariable int categoryId) {
        return resumeService.findByVacancyId(categoryId);
    }
}
