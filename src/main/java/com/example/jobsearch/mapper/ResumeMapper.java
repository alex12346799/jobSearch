package com.example.jobsearch.mapper;

import com.example.jobsearch.dto.EducationInfoRequestDto;
import com.example.jobsearch.dto.ResumeRequestDto;
import com.example.jobsearch.dto.WorkExperienceInfoRequestDto;
import com.example.jobsearch.model.EducationInfo;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.WorkExperienceInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResumeMapper {

    public static Resume fromDto(ResumeRequestDto dto) {
        Resume resume = new Resume();
        resume.setApplicantId(dto.getApplicantId());
        resume.setName(dto.getName());
        resume.setCategoryId(dto.getCategoryId());
        resume.setSalary(dto.getSalary());
        resume.setCreatedDate(LocalDateTime.now());
        resume.setUpdateDate(LocalDateTime.now());


        List<EducationInfo> educationList = new ArrayList<>();
        if (dto.getEducationInfoList() != null) {
            for (EducationInfoRequestDto eduDto : dto.getEducationInfoList()) {
                EducationInfo educationInfo = new EducationInfo();
                educationInfo.setInstitution(eduDto.getInstitution());
                educationInfo.setProgram(eduDto.getProgram());
                educationInfo.setStartDate(eduDto.getStartDate());
                educationInfo.setEndDate(eduDto.getEndDate());
                educationList.add(educationInfo);
            }
        }
        resume.setEducationInfoList(educationList);


        List<WorkExperienceInfo> workList = new ArrayList<>();
        if (dto.getWorkExperienceInfoList() != null) {
            for (WorkExperienceInfoRequestDto workDto : dto.getWorkExperienceInfoList()) {
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
