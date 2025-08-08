package com.example.jobsearch.service.impl;


import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.dao.VacancyDao;
import com.example.jobsearch.dao.impl.CategoryDao;
import com.example.jobsearch.dto.VacancyRequestDto;
import com.example.jobsearch.dto.VacancyResponseDto;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.mapper.ResumeMapper;
import com.example.jobsearch.mapper.VacancyMapper;
import com.example.jobsearch.model.User;
import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;


    @Override
    public void create(VacancyRequestDto vacancyRequestDto) {
        Optional<User> categoriId = userDao.findById(vacancyRequestDto.getCategoryId());
        if (categoriId.isEmpty()) {
            throw new NotFoundException("Пользователь с данным " + vacancyRequestDto.getCategoryId() + " Id не найден");
        }
        Optional<User> employerId = userDao.findById(vacancyRequestDto.getEmployerId());
        if (employerId.isEmpty()) {
            throw new NotFoundException("Пользователь с данным " + vacancyRequestDto.getEmployerId() + " Id не найден");
        }
        Vacancy vacancy = VacancyMapper.fromDto(vacancyRequestDto);
        vacancyDao.saveVacancy(vacancy);
    }


    @Override
    public Vacancy getById(long id) {
        return vacancyDao.findVacancyById(id)
                .orElseThrow(() -> new NotFoundException("Вакансия с таким id " + id + " не найден"));
    }


    @Override
    public List<VacancyResponseDto> getAll() {
        List<Vacancy> vacancies = vacancyDao.findAllVacancies();
        return vacancies.stream()
                .map(a -> VacancyResponseDto.builder()
                        .id(a.getId())
                        .title(a.getTitle())
                        .description(a.getDescription())
                        .categoryName(categoryDao.findNameById(a.getCategoryId()))
                        .salary(a.getSalary())
                        .expFrom(a.getExpFrom())
                        .expTo(a.getExpTo())
                        .isActive(a.isActive())
                        .employerName(userDao.findNameById(a.getEmployerId()))
                        .createdDate(a.getCreatedDate())
                        .updateDate(a.getUpdateDate())
                        .build())
                .toList();


    }

    @Override
    public List<Vacancy> getByUser(int userId) {
        return vacancyDao.findByUserId(userId);
    }

    @Override
    public void update(Vacancy vacancy, long id, Authentication auth) {
        String username = auth.getName();
        User user = userDao.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Vacancy exestingVacancy = vacancyDao.findVacancyById(id)
                .orElseThrow(() -> new NotFoundException("Вакансия не найдена"));
        if (exestingVacancy.getEmployerId() != user.getId()) {
            throw new NotFoundException("У вас нет прав для изменения этого ваканасии");
        }

        vacancyDao.updateVacancy(vacancy);
    }

    @Override
    public void delete(long id, Authentication auth) {
        String username = auth.getName();
        User user = userDao.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Vacancy exestingVacancy = vacancyDao.findVacancyById(id)
                .orElseThrow(() -> new NotFoundException("Вакансия не найдена"));
        if (exestingVacancy.getEmployerId() != user.getId()) {
            throw new NotFoundException("У вас нет прав для удаления этой ваканасии");
        }

        vacancyDao.deleteVacancyById(id);
    }


}

