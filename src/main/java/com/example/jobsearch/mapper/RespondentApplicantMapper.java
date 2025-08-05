package com.example.jobsearch.mapper;

import com.example.jobsearch.dto.RespondentApplicantRequestDto;
import com.example.jobsearch.dto.RespondentApplicantResponseDto;
import com.example.jobsearch.model.RespondentApplicant;

public class RespondentApplicantMapper {
    public static RespondentApplicantResponseDto toDto(RespondentApplicant respondentApplicant) {
        if (respondentApplicant == null) return null;
        RespondentApplicantResponseDto dto = new RespondentApplicantResponseDto();
        dto.setResumeId(respondentApplicant.getResumeId());
        dto.setVacancyId(respondentApplicant.getVacancyId());
        dto.setConfirmation(respondentApplicant.isConfirmation());
        dto.setCreateDate(respondentApplicant.getCreateDate());
        return dto;
    }
    public static RespondentApplicant fromDto(RespondentApplicantRequestDto requestDto) {
        if (requestDto == null) return null;
        RespondentApplicant dto = new RespondentApplicant();
        dto.setResumeId(requestDto.getResumeId());
        dto.setVacancyId(requestDto.getVacancyId());
        return dto;
    }
}
