package com.example.jobsearch.service.impl;


import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.dao.VacancyDao;
import com.example.jobsearch.dao.impl.CategoryDao;
import com.example.jobsearch.dto.VacancyRequestDto;
import com.example.jobsearch.dto.VacancyResponseDto;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.mapper.ResumeMapper;
import com.example.jobsearch.mapper.VacancyMapper;
import com.example.jobsearch.model.Category;
import com.example.jobsearch.model.User;
import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.repository.CategoryRepository;
import com.example.jobsearch.repository.UserRepository;
import com.example.jobsearch.repository.VacancyRepository;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public Vacancy create(VacancyRequestDto vacancyRequestDto) {
        Category category = categoryRepository.findById(vacancyRequestDto.getCategoryId())

                .orElseThrow(() -> new NotFoundException("Пользователь с данным " + vacancyRequestDto.getCategoryId() + " Id не найден"));

        User employer = userRepository.findById(vacancyRequestDto.getEmployerId())
                .orElseThrow(() -> new NotFoundException("Пользователь с данным " + vacancyRequestDto.getEmployerId() + " Id не найден"));

        Vacancy vacancy = VacancyMapper.fromDto(vacancyRequestDto, category, employer);
        vacancyRepository.save(vacancy);
        return vacancy;
    }


    @Override
    public Vacancy getById(long id) {
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Вакансия с таким id " + id + " не найден"));
    }


    @Override
    public List<VacancyResponseDto> getAll() {
        List<Vacancy> vacancies = vacancyRepository.findAll();
        return vacancies.stream()
                .map(a -> VacancyResponseDto.builder()
                        .id(a.getId())
                        .title(a.getTitle())
                        .description(a.getDescription())
                        .categoryName(categoryRepository.findNameById(a.getCategory().getId()))
                        .salary(a.getSalary())
                        .expFrom(a.getExpFrom())
                        .expTo(a.getExpTo())
                        .isActive(a.isActive())
                        .employerName(userRepository.findNameById(a.getEmployer().getId()))
                        .createdDate(a.getCreatedDate())
                        .updateDate(a.getUpdateDate())
                        .build())
                .toList();


    }

    @Override
    public List<Vacancy> getByUser(int userId) {
        return vacancyRepository.findByEmployerId(userId);
    }

    @Override
    public void update(Vacancy vacancy, long id, Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new NotFoundException("Пользователь с email " + email + " не найден"));


        if (!vacancy.getEmployer().getId().equals(user.getId())) {
            throw new NotFoundException("Вы не являетесь владельцем этой вакансии");
        }
       vacancy.setId(id);
        vacancyRepository.save(vacancy);
    }

    @Override
    public void delete(long id, Authentication auth) {
//        String username = auth.getName();
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
//        Vacancy exestingVacancy = vacancyRepository.findVacancyById(id)
//                .orElseThrow(() -> new NotFoundException("Вакансия не найдена"));
//        if (exestingVacancy.getEmployer().getId() != user.getId()) {
//            throw new NotFoundException("У вас нет прав для удаления этой ваканасии");
//        }
//
//        vacancyRepository.deleteVacancyById(id);
    }


}

