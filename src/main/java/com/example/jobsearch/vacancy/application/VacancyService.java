package com.example.jobsearch.vacancy.application;

import com.example.jobsearch.vacancy.web.VacancyRequest;
import com.example.jobsearch.vacancy.web.VacancyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VacancyService {
    Page<VacancyResponse> findAll(String search, Long categoryId, Pageable pageable);

    VacancyResponse findById(Long id);

    VacancyResponse create(VacancyRequest request, Authentication authentication);

    VacancyResponse update(Long id, VacancyRequest request, Authentication authentication);

    void delete(Long id, Authentication authentication);

    List<VacancyResponse> findByEmployer(Authentication authentication);
}
