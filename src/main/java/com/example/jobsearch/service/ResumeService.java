package com.example.jobsearch.service;

import com.example.jobsearch.dto.ResumeRequestDto;
import com.example.jobsearch.dto.ResumeResponseDto;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ResumeService {

    default Sort getSortMethod(String sortValue) {
        return switch (sortValue) {
            case "createdDate" -> Sort.by("createdDate");
            case "responses" -> Sort.by("responses");
            case "salary" -> Sort.by("salary");
            default -> Sort.by("name");
        };
    }

    List<ResumeResponseDto> getAllResumes();


    List<ResumeResponseDto> getAllSortedAndPagedResume(Pageable pageable);

    Resume getById(long id);



    List<Resume> findByApplicantId(Authentication auth);



    Resume create(ResumeRequestDto dto, Authentication auth);

    void update(Resume resume, long id, Authentication auth);


    void delete(long id, Authentication auth);
}
