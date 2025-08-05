package com.example.jobsearch.service.impl;


import com.example.jobsearch.dao.VacancyDao;
import com.example.jobsearch.dto.VacancyRequestDto;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.mapper.VacancyMapper;
import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.service.VacancyService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;

    public VacancyServiceImpl(VacancyDao vacancyDao) {
        this.vacancyDao = vacancyDao;
    }

    @Override
    public void create(VacancyRequestDto vacancyRequestDto) {
        Vacancy vacancy = VacancyMapper.fromDto(vacancyRequestDto);
        vacancyDao.saveVacancy(vacancy);
    }



    @Override
    public Vacancy getById(long id) {
        return vacancyDao.findVacancyById(id)
                .orElseThrow(() -> new NotFoundException("Вакансия с таким id " + id + " не найден"));
    }

    @Override
    public List<Vacancy> getAll() {
       return vacancyDao.findAllVacancies();
    }

    @Override
    public List<Vacancy> getByUser(int userId) {
        return vacancyDao.findByUserId(userId);
    }

    @Override
    public void update(Vacancy vacancy) {
        getById(vacancy.getId());
        vacancyDao.updateVacancy(vacancy);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(long id) {
        getById(id);
        vacancyDao.deleteVacancyById(id);

    }
}

