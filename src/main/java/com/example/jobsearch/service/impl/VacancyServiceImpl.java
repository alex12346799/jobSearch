package com.example.jobsearch.service.impl;

import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.storage.VacancyStorage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service

public class VacancyServiceImpl {
    private final VacancyStorage vacancyStorage;

    public VacancyServiceImpl(VacancyStorage vacancyStorage) {
        this.vacancyStorage = vacancyStorage;
    }

    public Vacancy create(Vacancy vacancy) {
        vacancy.setCreatedDate(java.time.LocalDateTime.now());
        vacancy.setUpdateTime(java.time.LocalDateTime.now());
        return vacancyStorage.save(vacancy);
    }

    public List<Vacancy> getAll() {
        return vacancyStorage.findAll();
    }

    public Optional<Vacancy> getById(Integer id) {
        return vacancyStorage.findById(id);
    }

    public void delete(int id) {
        vacancyStorage.deleteById(id);
    }

    public Optional<Vacancy> update(int id, Vacancy updated) {
        Optional<Vacancy> existing = vacancyStorage.findById(id);
        if (existing.isPresent()) {
            Vacancy vacancy = existing.get();
            vacancy.setTitle(updated.getTitle());
            vacancy.setDescription(updated.getDescription());
            vacancy.setCategoryId(updated.getCategoryId());
            vacancy.setSalary(updated.getSalary());
            vacancy.setExpFrom(updated.getExpFrom());
            vacancy.setExpTo(updated.getExpTo());
            vacancy.setActive(updated.isActive());
            vacancy.setUpdateTime(updated.getUpdateTime());
            return Optional.of(vacancy);
        }
        return Optional.empty();
    }

    public List<Vacancy> getByCategoryId(int categoryId) {
        return vacancyStorage.findAll().stream().filter(v -> v
                        .getCategoryId() == categoryId)
                .toList();
    }
    public List<Vacancy> findActive() {
        return vacancyStorage.findAll().stream()
                .filter(Vacancy::isActive)
                .toList();
    }
}

