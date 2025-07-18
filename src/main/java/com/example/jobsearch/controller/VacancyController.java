package com.example.jobsearch.controller;

import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.service.impl.VacancyServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vacancies")
public class VacancyController {
    private final VacancyServiceImpl vacancyService;

    public VacancyController(VacancyServiceImpl vacancies) {
        this.vacancyService = vacancies;
    }

    @PostMapping
    public ResponseEntity<Vacancy> create(@RequestBody Vacancy vacancy) {
        vacancyService.create(vacancy);
        return ResponseEntity.ok(vacancy);
    }

    @GetMapping
    public List<Vacancy> getAll() {
        return vacancyService.findAll();
    }

    @GetMapping("/{id}")
    public Vacancy getById(@PathVariable int id) {
        return vacancyService.findById(id);

    }

    @GetMapping("/employer/{employerId}")
    public List<Vacancy> getByEmployerId(@PathVariable int employerId) {
        return vacancyService.getEmployerById(employerId);
    }

    @GetMapping("/category/{categoryId}")
    public List<Vacancy> getByCategoryId(@PathVariable int categoryId) {
        return vacancyService.getByCategoryById(categoryId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> update(@PathVariable int id, @RequestBody Vacancy vacancy) {
        vacancyService.update(id, vacancy);
        return ResponseEntity.ok(vacancy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Vacancy> delete(@PathVariable int id) {
        vacancyService.delete(id);
        return ResponseEntity.ok().build();
    }


}
