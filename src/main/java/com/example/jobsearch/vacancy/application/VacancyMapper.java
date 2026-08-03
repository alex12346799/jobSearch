package com.example.jobsearch.vacancy.application;

import com.example.jobsearch.category.domain.Category;
import com.example.jobsearch.model.User;
import com.example.jobsearch.vacancy.domain.Vacancy;
import com.example.jobsearch.vacancy.web.VacancyRequest;
import com.example.jobsearch.vacancy.web.VacancyResponse;

final class VacancyMapper {
    private VacancyMapper() {
    }

    static VacancyResponse toResponse(Vacancy vacancy) {
        return new VacancyResponse(
                vacancy.getId(),
                vacancy.getTitle(),
                vacancy.getDescription(),
                vacancy.getCategory().getId(),
                vacancy.getCategory().getName(),
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.isActive(),
                vacancy.getEmployer().getId(),
                vacancy.getEmployer().getName(),
                vacancy.getCreatedDate(),
                vacancy.getUpdateDate()
        );
    }

    static Vacancy toEntity(VacancyRequest request, Category category, User employer) {
        Vacancy vacancy = new Vacancy();
        vacancy.setCategory(category);
        vacancy.setEmployer(employer);
        update(vacancy, request, category);
        return vacancy;
    }

    static void update(Vacancy vacancy, VacancyRequest request, Category category) {
        vacancy.setTitle(request.title().trim());
        vacancy.setDescription(request.description().trim());
        vacancy.setCategory(category);
        vacancy.setSalary(request.salary());
        vacancy.setExpFrom(request.expFrom());
        vacancy.setExpTo(request.expTo());
        vacancy.setActive(request.isActive());
    }
}
