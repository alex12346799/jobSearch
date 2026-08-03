package com.example.jobsearch.vacancy.application;

import com.example.jobsearch.category.domain.Category;
import com.example.jobsearch.category.persistence.CategoryRepository;
import com.example.jobsearch.exceptions.ResourceNotFoundException;
import com.example.jobsearch.model.User;
import com.example.jobsearch.repository.UserRepository;
import com.example.jobsearch.vacancy.application.exception.VacancyAccessDeniedException;
import com.example.jobsearch.vacancy.application.exception.VacancyInUseException;
import com.example.jobsearch.vacancy.application.exception.VacancyValidationException;
import com.example.jobsearch.vacancy.domain.Vacancy;
import com.example.jobsearch.vacancy.persistence.VacancyRepository;
import com.example.jobsearch.vacancy.web.VacancyRequest;
import com.example.jobsearch.vacancy.web.VacancyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<VacancyResponse> findAll(String search, Long categoryId, Pageable pageable) {
        String normalizedSearch = search == null ? "" : search.trim();
        return vacancyRepository.search(normalizedSearch, categoryId, pageable)
                .map(VacancyMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public VacancyResponse findById(Long id) {
        return VacancyMapper.toResponse(getVacancy(id));
    }

    @Override
    @Transactional
    public VacancyResponse create(VacancyRequest request, Authentication authentication) {
        ensureCanCreate(authentication);
        validate(request);
        User employer = getCurrentUser(authentication);
        Category category = getCategory(request.categoryId());
        Vacancy vacancy = VacancyMapper.toEntity(request, category, employer);
        return VacancyMapper.toResponse(vacancyRepository.save(vacancy));
    }

    @Override
    @Transactional
    public VacancyResponse update(Long id, VacancyRequest request, Authentication authentication) {
        Vacancy vacancy = getVacancy(id);
        ensureOwnerOrAdmin(vacancy, authentication);
        validate(request);
        Category category = getCategory(request.categoryId());
        VacancyMapper.update(vacancy, request, category);
        return VacancyMapper.toResponse(vacancyRepository.save(vacancy));
    }

    @Override
    @Transactional
    public void delete(Long id, Authentication authentication) {
        Vacancy vacancy = getVacancy(id);
        ensureOwnerOrAdmin(vacancy, authentication);
        if (vacancyRepository.hasResponses(id)) {
            throw new VacancyInUseException(id);
        }
        vacancyRepository.delete(vacancy);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VacancyResponse> findByEmployer(Authentication authentication) {
        return vacancyRepository.findByEmployerEmailOrderByIdDesc(authentication.getName()).stream()
                .map(VacancyMapper::toResponse)
                .toList();
    }

    private void validate(VacancyRequest request) {
        if (request.title() == null || request.title().trim().isEmpty()) {
            throw new VacancyValidationException("Title must not be blank");
        }
        if (request.description() == null || request.description().trim().isEmpty()) {
            throw new VacancyValidationException("Description must not be blank");
        }
        if (request.expFrom() != null && request.expTo() != null && request.expFrom() > request.expTo()) {
            throw new VacancyValidationException("Minimum experience must not exceed maximum experience");
        }
    }

    private Vacancy getVacancy(Long id) {
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy with id " + id + " was not found"));
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " was not found"));
    }

    private User getCurrentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user was not found"));
    }

    private void ensureCanCreate(Authentication authentication) {
        if (!hasAuthority(authentication, "ADMIN")
                && !hasAuthority(authentication, "EMPLOYER")) {
            throw new VacancyAccessDeniedException("Only an employer or administrator can create a vacancy");
        }
    }

    private void ensureOwnerOrAdmin(Vacancy vacancy, Authentication authentication) {
        if (hasAuthority(authentication, "ADMIN")) {
            return;
        }
        User currentUser = getCurrentUser(authentication);
        if (!vacancy.getEmployer().getId().equals(currentUser.getId())) {
            throw new VacancyAccessDeniedException("You are not allowed to modify this vacancy");
        }
    }

    private boolean hasAuthority(Authentication authentication, String authority) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
    }
}
