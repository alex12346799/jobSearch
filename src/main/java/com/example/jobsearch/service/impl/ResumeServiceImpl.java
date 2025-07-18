package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.model.Resume;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ResumeServiceImpl {
private final ResumeDao resumeDao;

    public ResumeServiceImpl(ResumeDao resumeDao) {
        this.resumeDao = resumeDao;
    }
    public Resume create(Resume resume) {
        resumeDao.save(resume);
        return resume;
    }
    public List<Resume> getAll() {
        return resumeDao.findAll();
    }
    public List<Resume> findByVacancyId(int vacancyId){
        return resumeDao.findCategoeyId(vacancyId);
    }
    public void delete(int id) {
        resumeDao.delete(id);
    }

    public Optional<Resume> update(int id, Resume update){
        update.setId(id);
        resumeDao.save(update);
        return Optional.of(update);
    }
    public Optional<Resume> findById(int id) {
        return resumeDao.findById(id);
    }
    public List<Resume> findByApplicantId(int applicantId) {
        return resumeDao.findCategoeyId(applicantId);
    }
}
