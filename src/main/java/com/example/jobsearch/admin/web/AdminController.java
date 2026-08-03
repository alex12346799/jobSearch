package com.example.jobsearch.admin.web;

import com.example.jobsearch.admin.application.AdminService;
import com.example.jobsearch.auth.application.AuthUnauthorizedException;
import com.example.jobsearch.jobapplication.domain.JobApplicationStatus;
import com.example.jobsearch.jobapplication.web.JobApplicationResponse;
import com.example.jobsearch.jobapplication.web.PageResponse;
import com.example.jobsearch.user.domain.RoleName;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService service;

    @GetMapping("/users")
    public PageResponse<AdminUserResponse> users(@RequestParam(defaultValue = "") String search,
                                                 @RequestParam(required = false) RoleName role,
                                                 @RequestParam(required = false) Boolean enabled,
                                                 @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.findUsers(search, role, enabled, pageable);
    }

    @GetMapping("/users/{id}")
    public AdminUserResponse user(@PathVariable long id) { return service.findUser(id); }

    @PatchMapping("/users/{id}/status")
    public AdminUserResponse status(@PathVariable long id, @Valid @RequestBody AdminUserStatusRequest request,
                                    @AuthenticationPrincipal Jwt jwt) {
        return service.setUserEnabled(userId(jwt), id, request.enabled());
    }

    @GetMapping("/job-applications")
    public PageResponse<JobApplicationResponse> applications(
            @RequestParam(required = false) Long vacancyId,
            @RequestParam(required = false) JobApplicationStatus status,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.findJobApplications(vacancyId, status, pageable);
    }

    private long userId(Jwt jwt) {
        try { return Long.parseLong(jwt.getSubject()); }
        catch (RuntimeException exception) { throw new AuthUnauthorizedException(); }
    }
}
