package com.example.jobsearch.mapper;

import com.example.jobsearch.dto.EducationInfoRequestDto;
import com.example.jobsearch.dto.EducationInfoResponseDto;
import com.example.jobsearch.model.EducationInfo;
import com.example.jobsearch.model.Resume;

public class EducationInfoMapper {
//    public static EducationInfoResponseDto toDto(EducationInfo educationInfo) {
//        if (educationInfo == null) return null;
//        EducationInfoResponseDto dto = new EducationInfoResponseDto();
//        dto.setId(educationInfo.getId());
//        dto.setResumeId(educationInfo.getResume().getId());
//        dto.setInstitution(educationInfo.getInstitution());
//        dto.setProgram(educationInfo.getProgram());
//        dto.setStartDate(educationInfo.getStartDate());
//        dto.setEndDate(educationInfo.getEndDate());
//        dto.setDegree(educationInfo.getDegree());
//        return dto;
//    }
//    public static EducationInfo fromDto(EducationInfoRequestDto dto){
//        if (dto == null) return null;
//        Resume resume = new Resume();
//        EducationInfo educationInfo = new EducationInfo();
////        resume.setId(dto.getResumeId());
//        educationInfo.setResume(resume);
//        educationInfo.setInstitution(dto.getInstitution());
//        educationInfo.setProgram(dto.getProgram());
//        educationInfo.setStartDate(dto.getStartDate());
//        educationInfo.setEndDate(dto.getEndDate());
//        educationInfo.setDegree(dto.getDegree());
//        return educationInfo;
//    }
public static EducationInfoResponseDto toDto(EducationInfo educationInfo) {
    if (educationInfo == null) return null;

    EducationInfoResponseDto dto = new EducationInfoResponseDto();
    dto.setId(educationInfo.getId());
    dto.setResumeId(educationInfo.getResume().getId());
    dto.setInstitution(educationInfo.getInstitution());
    dto.setProgram(educationInfo.getProgram());
    dto.setStartDate(educationInfo.getStartDate());
    dto.setEndDate(educationInfo.getEndDate());
    dto.setDegree(educationInfo.getDegree());
    return dto;
}

    public static EducationInfo fromDto(EducationInfoRequestDto dto){
        if (dto == null) return null;

        EducationInfo educationInfo = new EducationInfo();
        educationInfo.setInstitution(dto.getInstitution());
        educationInfo.setProgram(dto.getProgram());
        educationInfo.setStartDate(dto.getStartDate());
        educationInfo.setEndDate(dto.getEndDate());
        educationInfo.setDegree(dto.getDegree());
        return educationInfo;
    }
}
