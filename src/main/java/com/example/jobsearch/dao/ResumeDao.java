package com.example.jobsearch.dao;

import com.example.jobsearch.model.Resume;

import java.util.List;
import java.util.Optional;

public interface ResumeDao {
    List<Resume> findAll();

    Optional<Resume> findById(int id);

    List<Resume> findAllByApplicantId(int applicantId);

    Resume save(Resume resume);

    void update(Resume resume);

    void deleteById(int id);
}
