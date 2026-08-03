package com.example.jobsearch.resume.application;

import com.example.jobsearch.resume.domain.*;
import com.example.jobsearch.resume.web.*;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class ResumeMapper {
    public ResumeResponse toResponse(Resume resume) {
        var education = resume.getEducationInfo().stream().sorted(Comparator.comparing(EducationInfo::getId,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .map(e -> new ResumeResponse.EducationInfoResponse(e.getId(), e.getInstitution(), e.getProgram(),
                        e.getStartDate(), e.getEndDate(), e.getDegree())).toList();
        var work = resume.getWorkExperienceInfo().stream().sorted(Comparator.comparing(WorkExperienceInfo::getId,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .map(w -> new ResumeResponse.WorkExperienceInfoResponse(w.getId(), w.getStartDate(), w.getEndDate(),
                        w.getCompanyName(), w.getPosition(), w.getResponsibilities())).toList();
        SocialLinks links = resume.getSocialLinks();
        var linksResponse = links == null ? null : new ResumeResponse.SocialLinksResponse(
                links.getId(), links.getTelegram(), links.getFacebook(), links.getLinkedin());
        return new ResumeResponse(resume.getId(), resume.getApplicant().getId(), resume.getApplicant().getName(),
                resume.getName(), resume.getCategory().getId(), resume.getCategory().getName(), resume.getSalary(),
                resume.isActive(), resume.getCreatedDate(), resume.getUpdateDate(), education, work, linksResponse);
    }
}
