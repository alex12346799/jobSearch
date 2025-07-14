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
private final VacancyService vacancyService;

    public VacancyController(VacancyService vacancies) {
        this.vacancyService = vacancies;
    }
    @GetMapping
    public List<Vacancy> findAll() {
        return vacancyService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getById(@PathVariable int id) {
        Optional<Vacancy> vacancy = vacancyService.getById(id);
        return vacancy.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/category{categoryId}")
    public List<Vacancy> getByCategoryId(@PathVariable int categoryId) {
        return vacancyService.getByCategoryId(categoryId);
    }

    @PostMapping
    public ResponseEntity<Vacancy> create(@RequestBody Vacancy vacancy) {
Vacancy saved = vacancyService.create(vacancy);
return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Vacancy> delete(@PathVariable int id) {
        Optional<Vacancy> vacancy = vacancyService.getById(id);
        if (vacancy.isPresent()) {
            vacancyService.delete(id);
            return ResponseEntity.ok().build();
        }return  ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> update(@PathVariable int id, @RequestBody Vacancy vacancy) {
        Optional<Vacancy> updated = vacancyService.update(id, vacancy);
        return updated.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
