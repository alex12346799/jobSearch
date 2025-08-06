package com.example.jobsearch.controller;


import com.example.jobsearch.dto.VacancyRequestDto;
import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.service.VacancyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<String> createVacancy(@RequestBody @Valid VacancyRequestDto vacancyRequestDto) {
        vacancyService.create(vacancyRequestDto);
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
    public ResponseEntity<String> updateVacancy(@PathVariable int id, @RequestBody Vacancy vacancy, Authentication auth) {
        vacancy.setId(id);
        vacancyService.update(vacancy,id,auth);
        return ResponseEntity.ok("Вакансия обновлена");
    }

    @DeleteMapping("/{id}")
    public void deleteVacancyById(@PathVariable int id, Authentication auth) {
        vacancyService.delete(id, auth);

    }
}
