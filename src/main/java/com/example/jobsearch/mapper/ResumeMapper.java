package com.example.jobsearch.mapper;

import com.example.jobsearch.dto.EducationInfoDto;
import com.example.jobsearch.dto.ResumeCreateDto;
import com.example.jobsearch.dto.WorkExperienceInfoDto;
import com.example.jobsearch.model.EducationInfo;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.WorkExperienceInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResumeMapper {

    public static Resume fromDto(ResumeCreateDto dto) {
        Resume resume = new Resume();
        resume.setApplicantId(dto.getApplicantId());
        resume.setName(dto.getName());
        resume.setCategoryId(dto.getCategoryId());
        resume.setSalary(dto.getSalary());
        resume.setActive(dto.getIsActive());
        resume.setCreatedDate(LocalDateTime.now());
        resume.setUpdateTime(LocalDateTime.now());


        List<EducationInfo> educationList = new ArrayList<>();
        if (dto.getEducationInfoList() != null) {
            for (EducationInfoDto eduDto : dto.getEducationInfoList()) {
                EducationInfo educationInfo = new EducationInfo();
                educationInfo.setInstitution(eduDto.getInstitution());
                educationInfo.setProgram(eduDto.getProgram());
                educationInfo.setStartDate(LocalDate.parse(eduDto.getStartDate()));
                educationInfo.setEndDate(LocalDate.parse(eduDto.getEndDate()));
                educationList.add(educationInfo);
            }
        }
        resume.setEducationInfoList(educationList);


        List<WorkExperienceInfo> workList = new ArrayList<>();
        if (dto.getWorkExperienceInfoList() != null) {
            for (WorkExperienceInfoDto workDto : dto.getWorkExperienceInfoList()) {
                WorkExperienceInfo work = new WorkExperienceInfo();
                work.setCompanyName(workDto.getCompanyName());
                work.setPosition(workDto.getPosition());
                work.setYears(workDto.getYears());
                workList.add(work);
            }
        }
        resume.setWorkExperienceInfoList(workList);

        return resume;
    }
}
