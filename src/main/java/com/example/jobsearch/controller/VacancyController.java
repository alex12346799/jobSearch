package com.example.jobsearch.controller;


import com.example.jobsearch.dto1.VacancyCreateDto;
import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.service.VacancyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacancies")
public class VacancyController {
    private final VacancyService vacancyService;

    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }


    @PostMapping
    public ResponseEntity<String> createVacancy(@RequestBody @Valid VacancyCreateDto vacancyDto) {
        vacancyService.create(vacancyDto);
        return ResponseEntity.ok("Вакансия создана");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable int id) {
        return ResponseEntity.ok(vacancyService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        return ResponseEntity.ok(vacancyService.getAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Vacancy>> getVacanciesByUser(@PathVariable int userId) {
        return ResponseEntity.ok(vacancyService.getByUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVacancy(@PathVariable int id, @RequestBody Vacancy vacancy) {
        vacancy.setId(id);
        vacancyService.update(vacancy);
        return ResponseEntity.ok("Вакансия обновлена");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVacancyById(@PathVariable int id) {
        vacancyService.delete(id);
        return ResponseEntity.ok("Вакансия удалена");
    }
}
