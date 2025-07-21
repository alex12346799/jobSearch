package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;


    @Override
    public List<Resume> getAllResumes() {
        return resumeDao.findAll();
    }

    @Override
    public Resume getById(int id) {
        return resumeDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Вот это " + id + " резюме не найдено"));
    }

    @Override
    public List<Resume> getAllByApplicantId(int applicantId) {
        return resumeDao.findAllByApplicantId(applicantId);
    }

    @Override
    public void create(Resume resume) {
        resumeDao.save(resume);
    }

    @Override
    public void update(int id, Resume resume) {
        if (resumeDao.findById(id).isEmpty()) {
            throw new NotFoundException("Ошибка. Резюме с таким " + id + " не найдено");
        } else {
            resume.setId(id);
            resumeDao.update(resume);
        }
    }

    @Override
    public void delete(int id) {
        if (resumeDao.findById(id).isEmpty()) {
            throw new NotFoundException("Ошибка. Не могу удалить резюме так как. Резюме с таким " + id + " не найдено");
        } else {
            resumeDao.deleteById(id);
        }
    }
}
