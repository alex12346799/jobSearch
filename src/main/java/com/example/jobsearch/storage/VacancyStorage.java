package com.example.jobsearch.storage;

import com.example.jobsearch.model.Vacancy;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class VacancyStorage {
    Map<Integer, Vacancy> vacancies = new HashMap<>();
    private int IdCounter = 1;
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(IdCounter++);
        vacancy.setCreatedDate(java.time.LocalDateTime.now());
        vacancy.setUpdateTime(java.time.LocalDateTime.now());
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }
    public List<Vacancy> findAll() {
        return new ArrayList<>(vacancies.values());

    }
    public Optional<Vacancy> findById(Integer id) {
        return Optional.ofNullable(vacancies.get(id));
    }
    public void deleteById(Integer id) {
        vacancies.remove(id);
    }
}
