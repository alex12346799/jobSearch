package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.VacancyDao;

import com.example.jobsearch.model.Vacancy;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class VacancyServiceImpl {
    private final VacancyDao vacancyDao;

    public VacancyServiceImpl(VacancyDao vacancyDao) {
        this.vacancyDao = vacancyDao;
    }
    public VacancyDao create (Vacancy vacancy) {
        vacancyDao.save(vacancy);
        return vacancyDao;
    }
    public List<Vacancy> findAll() {
        return vacancyDao.findAll();
    }
    public List<Vacancy> getEmployerById(int employerId) {
        return vacancyDao.findByEmployerId(employerId);
    }
    public List<Vacancy> getByCategoryById(int categoryId) {
        return vacancyDao.findByVacancyId(categoryId);
    }
public Vacancy findById(int id) {
        return vacancyDao.findById(id);
}
public Vacancy update(int id, Vacancy update) {
        update.setId(id);
        vacancyDao.save(update);
        return update;
}
public void delete(int id) {
        vacancyDao.deleteById(id);

}
}

