package com.example.jobsearch.service;

import com.example.jobsearch.model.Resume;

import java.util.List;

public interface ResumeService {
    List<Resume> getAllResumes();

    Resume getById(int id);

    List<Resume> getAllByApplicantId(int applicantId);

    void create(Resume resume);

    void createWithDetails(Resume resume);

    void update(int id, Resume resume);

    void delete(int id);
}
