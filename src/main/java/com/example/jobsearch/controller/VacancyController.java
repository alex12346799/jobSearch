package com.example.jobsearch.controller;


import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.service.VacancyService;
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
    public ResponseEntity<String> createVacancy(@RequestBody Vacancy vacancy) {
        vacancyService.create(vacancy);
        return ResponseEntity.ok("Вакансия создана");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable int id) {
        return ResponseEntity.ok(vacancyService.getById(id));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Vacancy>> getVacanciesByUser(@PathVariable int userId) {
        return ResponseEntity.ok(vacancyService.getByUser(userId));
    }
    @PutMapping
    public ResponseEntity<String> updateVacancy(@RequestBody Vacancy vacancy) {
        vacancyService.update(vacancy);
        return ResponseEntity.ok("Вакансия обновлена");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVacancyById(@PathVariable int id) {
        vacancyService.delete(id);
       return ResponseEntity.ok("Вакансия удалена");
    }
}
