package com.example.jobsearch.dao;

import com.example.jobsearch.model.Resume;

import java.util.List;

public interface ResumeDao {
    Resume getById(Long id);
    List<Resume> findAll();
    List<Resume> findByApplicantId(int applicantId);
    void save(Resume resume);
    void update(Resume resume);

    void delete(long id);
}
