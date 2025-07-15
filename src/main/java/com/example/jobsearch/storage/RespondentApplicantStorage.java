package com.example.jobsearch.storage;

import com.example.jobsearch.model.RespondentApplicant;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RespondentApplicantStorage {

    private final Map<Integer, RespondentApplicant> ras = new HashMap<>();
    private int curentId = 1;

    public RespondentApplicant save(RespondentApplicant respondentApplicant) {
        respondentApplicant.setId(++curentId);
        ras.put(respondentApplicant.getId(), respondentApplicant);
        return respondentApplicant;
    }

    public List<RespondentApplicant> findAll() {
        return new ArrayList<>(ras.values());
    }

    public List<RespondentApplicant> findByResumeId(int resumeId) {
        return ras.values().stream().filter(r -> r
                        .getResumeId() == resumeId)
                .collect(Collectors.toList());
    }

    public List<RespondentApplicant> findByVacancyId(int vacancyId) {
        return ras.values().stream().filter(r -> r
                        .getVacancyId() == vacancyId)
                .collect(Collectors.toList());
    }

}
