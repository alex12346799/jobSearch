package com.example.jobsearch.mapper;

import com.example.jobsearch.dto.RespondentApplicantRequestDto;
import com.example.jobsearch.dto.RespondentApplicantResponseDto;
import com.example.jobsearch.model.RespondentApplicant;
import com.example.jobsearch.resume.domain.Resume;
import com.example.jobsearch.vacancy.domain.Vacancy;

public class RespondentApplicantMapper {
    public static RespondentApplicantResponseDto toDto(RespondentApplicant respondentApplicant) {
        if (respondentApplicant == null) return null;
        RespondentApplicantResponseDto dto = new RespondentApplicantResponseDto();
        dto.setResumeId(respondentApplicant.getResume().getId());
        dto.setVacancyId(respondentApplicant.getVacancy().getId());
        dto.setConfirmation(respondentApplicant.isConfirmation());
        dto.setCreateDate(respondentApplicant.getCreateDate());
        return dto;
    }
    public static RespondentApplicant fromDto(RespondentApplicantRequestDto requestDto) {
        if (requestDto == null) return null;
        RespondentApplicant dto = new RespondentApplicant();
        Resume resume = new Resume();
        resume.setId(requestDto.getResumeId());
        dto.setResume(resume);
        Vacancy vacancy = new Vacancy();
        vacancy.setId(requestDto.getVacancyId());
        dto.setVacancy(vacancy);
        return dto;
    }
}
