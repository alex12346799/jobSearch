package com.example.jobsearch.resume.web;

import com.example.jobsearch.auth.application.AuthUnauthorizedException;
import com.example.jobsearch.resume.application.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @GetMapping
    public PageResponse<ResumeResponse> findAll(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(required = false) Long categoryId,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return resumeService.findAll(search, categoryId, pageable, role(jwt));
    }

    @GetMapping("/{id}")
    public ResumeResponse findById(@PathVariable long id, @AuthenticationPrincipal Jwt jwt) {
        return resumeService.findById(id, userId(jwt), role(jwt));
    }

    @GetMapping("/me")
    public List<ResumeResponse> findMine(@AuthenticationPrincipal Jwt jwt) {
        return resumeService.findMine(userId(jwt));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResumeResponse create(@Valid @RequestBody ResumeRequest request, @AuthenticationPrincipal Jwt jwt) {
        return resumeService.create(userId(jwt), request);
    }

    @PutMapping("/{id}")
    public ResumeResponse update(@PathVariable long id, @Valid @RequestBody ResumeRequest request,
                                 @AuthenticationPrincipal Jwt jwt) {
        return resumeService.update(id, userId(jwt), role(jwt), request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id, @AuthenticationPrincipal Jwt jwt) {
        resumeService.delete(id, userId(jwt), role(jwt));
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
