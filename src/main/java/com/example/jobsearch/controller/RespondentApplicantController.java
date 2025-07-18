package com.example.jobsearch.controller;

import com.example.jobsearch.model.RespondentApplicant;
import com.example.jobsearch.service.impl.RespondentApplicantServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/respondents")
public class RespondentApplicantController {
//    private final RespondentApplicantServiceImpl ras;
//
//    public RespondentApplicantController(RespondentApplicantServiceImpl ras) {
//        this.ras = ras;
//    }
//    @PostMapping
//    public ResponseEntity<RespondentApplicant> respond (@RequestBody RespondentApplicant respondentApplicant) {
//        RespondentApplicant saved = ras.respondToVacancy(respondentApplicant);
//        return ResponseEntity.ok(saved);
//    }
//    @GetMapping
//    public List<RespondentApplicant> findAll() {
//        return ras.findAll();
//    }
//    @GetMapping("/vacancy/{vacancyId}")
//    public List<RespondentApplicant> findByVacancyId(@PathVariable int vacancyId) {
//        return ras.findByVacancyId(vacancyId);
//    }
//    @GetMapping("/resume/{resumeId}")
//    public List<RespondentApplicant> findByResumeId(@PathVariable int resumeId) {
//        return ras.findByResume(resumeId);
//    }
    private final RespondentApplicantServiceImpl respondentApplicantService;

    public RespondentApplicantController(RespondentApplicantServiceImpl respondentApplicantService) {
        this.respondentApplicantService = respondentApplicantService;
    }
    @GetMapping("/vacancy/{vacancyId}")
    public ResponseEntity<List<RespondentApplicant>> getRespondentsByVacancyId(@PathVariable int vacancyId) {
        List<RespondentApplicant>respondentApplicants = respondentApplicantService.getRespondentsByVacancyId(vacancyId);
        return ResponseEntity.ok(respondentApplicants);
    }
}
