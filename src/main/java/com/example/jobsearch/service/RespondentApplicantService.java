package com.example.jobsearch.service;

import com.example.jobsearch.model.RespondentApplicant;
import com.example.jobsearch.storage.RespondentApplicantStorage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RespondentApplicantService {
    private final RespondentApplicantStorage ras;

    public RespondentApplicantService(RespondentApplicantStorage ras) {
        this.ras = ras;
    }
    public RespondentApplicant respondToVacancy(RespondentApplicant respondentApplicant) {
     return ras.save(respondentApplicant);

    }
    public List<RespondentApplicant> findAll() {
        return ras.findAll();
    }
    public List<RespondentApplicant> findByVacancyId(int vacancyId) {
        return ras.findByVacancyId(vacancyId);
    }
    public List<RespondentApplicant> findByResume(int resumeId) {
        return ras.findByResumeId(resumeId);
    }
}
