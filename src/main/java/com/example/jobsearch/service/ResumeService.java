package com.example.jobsearch.service;

import com.example.jobsearch.model.Resume;
import com.example.jobsearch.storage.ResumeStorage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResumeService {
    private final ResumeStorage resumeStorage;

    public ResumeService(ResumeStorage resumeStorage) {
        this.resumeStorage = resumeStorage;
    }
  public Resume create(Resume resume) {
        return resumeStorage.save(resume);
  }
public List<Resume> getAll(){
        return resumeStorage.findAll();
}

public Optional<Resume> findById(int resumeId) {
        return resumeStorage.findById(resumeId);
}
public void delete(int resumeId) {
        resumeStorage.delete(resumeId);
}

public List<Resume> findByVacancyId(int vacancyId) {
        return resumeStorage.findByCategoryId(vacancyId);
}
public Optional<Resume> update(int id, Resume update) {
Optional<Resume> existing = resumeStorage.findById(id);
if (existing.isPresent()) {
    Resume resume = existing.get();
    resume.setName(update.getName());
    resume.setCategoryId(update.getCategoryId());
    resume.setSalary(update.getSalary());
    resume.setActive(update.isActive());
    resume.setUpdateTime(update.getUpdateTime());
    return Optional.of(resume);
}
return Optional.empty();
}
}
