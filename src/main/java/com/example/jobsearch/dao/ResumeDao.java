package com.example.jobsearch.dao;

import com.example.jobsearch.dto.ResumeResponseDto;
import com.example.jobsearch.model.Resume;

import java.util.List;
import java.util.Optional;

public interface ResumeDao {
    List<Resume> findAll();

    Optional<Resume> findById(long id);

//    List<Resume>  findByApplicantIdName(long applicantId);


    String findApplicantNameById(long applicantId);

    List<Resume> findAllByApplicantId(int applicantId);

    Resume save(Resume resume);

    void update(Resume resume);

    void deleteById(long id);
}
