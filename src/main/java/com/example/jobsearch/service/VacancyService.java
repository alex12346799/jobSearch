package com.example.jobsearch.service;

import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.storage.VacancyStorage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacancyService {
    private final VacancyStorage vacancies;

    public VacancyService(VacancyStorage vacancies) {
        this.vacancies = vacancies;
    }
    public Vacancy create(Vacancy vacancy) {
        return vacancies.save(vacancy);
    }
    public List<Vacancy> getAll() {
        return vacancies.findAll();
    }
    public Optional<Vacancy> getById(Integer id) {
        return vacancies.findById(id);
    }
    public void delete(int id){
        vacancies.deleteById(id);
    }
}

