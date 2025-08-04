package com.example.jobsearch.service;

import com.example.jobsearch.model.Resume;

import java.util.List;

public interface ResumeService {
    List<Resume> getAllResumes();

    Resume getById(long id);

    List<Resume> getAllByApplicantId(int applicantId);

    void create(Resume resume);

    void createWithDetails(Resume resume);

    void update(long id, Resume resume);

    void delete(long id);

}
