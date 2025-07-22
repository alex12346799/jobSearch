package com.example.jobsearch.service.impl;

import com.example.jobsearch.model.RespondentApplicant;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RespondentApplicantServiceImpl {
//    private final RespondentApplicantStorage ras;
//
//    public RespondentApplicantServiceImpl(RespondentApplicantStorage ras) {
//        this.ras = ras;
//    }
//    public RespondentApplicant respondToVacancy(RespondentApplicant respondentApplicant) {
//     return ras.save(respondentApplicant);
//
//    }
//    public List<RespondentApplicant> findAll() {
//        return ras.findAll();
//    }
//    public List<RespondentApplicant> findByVacancyId(int vacancyId) {
//        return ras.findByVacancyId(vacancyId);
//    }
//    public List<RespondentApplicant> findByResume(int resumeId) {
//        return ras.findByResumeId(resumeId);
//    }
   private final RespondentApplicationDao respondentApplicationDao;

    public RespondentApplicantServiceImpl(RespondentApplicationDao respondentApplicationDao) {
        this.respondentApplicationDao = respondentApplicationDao;
    }

    public List<RespondentApplicant> getRespondentsByVacancyId(int vacancyId) {
   return respondentApplicationDao.findAllByVacancyId(vacancyId);
    }
}
