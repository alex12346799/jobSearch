package com.example.jobsearch.mapper;

import com.example.jobsearch.dto.EducationInfoRequestDto;
import com.example.jobsearch.dto.ResumeRequestDto;
import com.example.jobsearch.dto.SocialLinkRequestDto;
import com.example.jobsearch.dto.WorkExperienceInfoRequestDto;
import com.example.jobsearch.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResumeMapper {

    public static Resume fromDto(ResumeRequestDto dto) {
        Resume resume = new Resume();

        User applicant = new User();
        applicant.setId(dto.getApplicantId());
        resume.setApplicant(applicant);

        Category category = new Category();
        category.setId(dto.getCategoryId());
        resume.setCategory(category);

        resume.setName(dto.getName());
        resume.setSalary(dto.getSalary());
        resume.setCreatedDate(LocalDateTime.now());
        resume.setUpdateDate(LocalDateTime.now());
        resume.setActive(dto.getIsActive());


        resume.setEducationInfoList(new ArrayList<>());
        resume.setWorkExperienceInfoList(new ArrayList<>());


        return resume;
    }

    public static SocialLinks fromDto(SocialLinkRequestDto dto) {
        if (dto == null) return null;

        SocialLinks socialLinks = new SocialLinks();
        socialLinks.setTelegram(dto.getTelegram());
        socialLinks.setFacebook(dto.getFacebook());
        socialLinks.setLinkedin(dto.getLinkedin());
        return socialLinks;
    }

}
