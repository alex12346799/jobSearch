package com.example.jobsearch.controller;

import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.service.VacancyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vacancies")
public class VacancyController {
private final VacancyService vacancies;

    public VacancyController(VacancyService vacancies) {
        this.vacancies = vacancies;
    }
    @GetMapping
    public List<Vacancy> findAll() {
        return vacancies.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getById(@PathVariable int id) {
        Optional<Vacancy> vacancy = vacancies.getById(id);
        return vacancy.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Vacancy> create(@RequestBody Vacancy vacancy) {
Vacancy saved = vacancies.create(vacancy);
return ResponseEntity.ok(saved);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Vacancy> delete(@PathVariable int id) {
        Optional<Vacancy> vacancy = vacancies.getById(id);
        if (vacancy.isPresent()) {
            vacancies.delete(id);
            return ResponseEntity.ok().build();
        }return  ResponseEntity.notFound().build();
    }
}
