package com.example.jobsearch.storage;

import com.example.jobsearch.model.Resume;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ResumeStorage {
    private Map<Integer, Resume> resumes = new HashMap<>();
    private int currentId = 1;
    public Resume save(Resume resume) {
        resume.setId(++currentId);
        resume.setCreatedDate(LocalDateTime.now());
        resume.setUpdateTime(LocalDateTime.now());
        resumes.put(resume.getId(), resume);
        return resume;
    }

    public List<Resume> findAll() {
        return new ArrayList<>(resumes.values());
    }

    public Optional<Resume> findById(int id){
        return Optional.ofNullable(resumes.get(id));
    }

    public void delete(int id) {
        resumes.remove(id);
    }

    public List<Resume> findByCategoryId(int categoryId) {
        return resumes.values().stream().filter(r -> r
                .getCategoryId() == categoryId)
                .collect(Collectors.toList());
    }
}
