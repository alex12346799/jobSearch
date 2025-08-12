package com.example.jobsearch.service;

import com.example.jobsearch.dto.ResumeRequestDto;
import com.example.jobsearch.dto.ResumeResponseDto;
import com.example.jobsearch.model.Resume;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ResumeService {
    List<ResumeResponseDto> getAllResumes();

    Resume getById(long id);

    List<Resume> getAllByApplicantId(int applicantId);



    Resume create(ResumeRequestDto dto);








    void update(Resume resume, long id, Authentication auth);



    void delete(long id, Authentication auth);
}
