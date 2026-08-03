package com.example.jobsearch.jobapplication.web;

import com.example.jobsearch.auth.application.AuthUnauthorizedException;
import com.example.jobsearch.jobapplication.application.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JobApplicationController {
    private final JobApplicationService service;

    @PostMapping("/api/v1/job-applications")
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplicationResponse create(@Valid @RequestBody JobApplicationRequest request,
                                         @AuthenticationPrincipal Jwt jwt) {
        return service.create(userId(jwt), request);
    }

    @GetMapping("/api/v1/job-applications/me")
    public PageResponse<JobApplicationResponse> findMine(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal Jwt jwt) {
        return service.findMine(userId(jwt), pageable);
    }

    @GetMapping("/api/v1/job-applications/{id}")
    public JobApplicationResponse findById(@PathVariable long id, @AuthenticationPrincipal Jwt jwt) {
        return service.findById(id, userId(jwt), role(jwt));
    }

    @GetMapping("/api/v1/vacancies/{vacancyId}/applications")
    public PageResponse<JobApplicationResponse> findForVacancy(
            @PathVariable long vacancyId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal Jwt jwt) {
        return service.findForVacancy(vacancyId, userId(jwt), role(jwt), pageable);
    }

    @PatchMapping("/api/v1/job-applications/{id}/status")
    public JobApplicationResponse updateStatus(@PathVariable long id,
                                               @Valid @RequestBody UpdateApplicationStatusRequest request,
                                               @AuthenticationPrincipal Jwt jwt) {
        return service.updateStatus(id, userId(jwt), role(jwt), request.status());
    }

    @DeleteMapping("/api/v1/job-applications/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrWithdraw(@PathVariable long id, @AuthenticationPrincipal Jwt jwt) {
        service.deleteOrWithdraw(id, userId(jwt), role(jwt));
    }

    private long userId(Jwt jwt) {
        try { return Long.parseLong(jwt.getSubject()); }
        catch (RuntimeException exception) { throw new AuthUnauthorizedException(); }
    }

    private String role(Jwt jwt) {
        String role = jwt.getClaimAsString("role");
        if (role == null) throw new AuthUnauthorizedException();
        return role;
    }
}
