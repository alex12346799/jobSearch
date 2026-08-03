package com.example.jobsearch.jobapplication.domain;

import com.example.jobsearch.resume.domain.Resume;
import com.example.jobsearch.vacancy.domain.Vacancy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "respondent_applicant",
        uniqueConstraints = @UniqueConstraint(name = "uq_job_application_vacancy_resume",
                columnNames = {"vacancy_id", "resume_id"}))
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vacancy_id", nullable = false)
    private Vacancy vacancy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private JobApplicationStatus status;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "update_date", nullable = false)
    private LocalDateTime updatedAt;
}
