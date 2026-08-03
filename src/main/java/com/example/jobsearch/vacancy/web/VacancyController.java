package com.example.jobsearch.vacancy.web;

import com.example.jobsearch.vacancy.application.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping
    public PageResponse<VacancyResponse> findAll(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(required = false) Long categoryId,
            @PageableDefault(sort = "id") Pageable pageable
    ) {
        return PageResponse.from(vacancyService.findAll(search, categoryId, pageable));
    }

    @GetMapping("/{id}")
    public VacancyResponse findById(@PathVariable Long id) {
        return vacancyService.findById(id);
    }

    @PostMapping
    public ResponseEntity<VacancyResponse> create(
            @Valid @RequestBody VacancyRequest request,
            Authentication authentication
    ) {
        VacancyResponse response = vacancyService.create(request, authentication);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public VacancyResponse update(
            @PathVariable Long id,
            @Valid @RequestBody VacancyRequest request,
            Authentication authentication
    ) {
        return vacancyService.update(id, request, authentication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        vacancyService.delete(id, authentication);
        return ResponseEntity.noContent().build();
    }
}
