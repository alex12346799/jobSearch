package com.example.jobsearch.resume.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

public record ResumeResponse(
        Long id,
        Long applicantId,
        String applicantName,
        String name,
        Long categoryId,
        String categoryName,
        BigDecimal salary,
        boolean active,
        LocalDateTime createdDate,
        LocalDateTime updateDate,
        List<EducationInfoResponse> education,
        List<WorkExperienceInfoResponse> workExperience,
        SocialLinksResponse socialLinks
) {
    public record EducationInfoResponse(Long id, String institution, String program,
                                        LocalDate startDate, LocalDate endDate, String degree) {}
    public record WorkExperienceInfoResponse(Long id, LocalDate startDate, LocalDate endDate,
                                             String companyName, String position, String responsibilities) {}
    public record SocialLinksResponse(Long id, String telegram, String facebook, String linkedin) {}
}
