package com.example.jobsearch.jobapplication.persistence;

import com.example.jobsearch.jobapplication.domain.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    boolean existsByVacancyIdAndResumeId(Long vacancyId, Long resumeId);

    @EntityGraph(attributePaths = {"resume", "resume.applicant", "vacancy", "vacancy.employer"})
    Optional<JobApplication> findDetailedById(Long id);

    @EntityGraph(attributePaths = {"resume", "resume.applicant", "vacancy", "vacancy.employer"})
    Page<JobApplication> findByResumeApplicantIdOrderByCreatedAtDesc(Long applicantId, Pageable pageable);

    @EntityGraph(attributePaths = {"resume", "resume.applicant", "vacancy", "vacancy.employer"})
    Page<JobApplication> findByVacancyIdOrderByCreatedAtDesc(Long vacancyId, Pageable pageable);

    @Query("select (count(a) > 0) from JobApplication a where a.vacancy.id = :vacancyId")
    boolean hasApplicationsForVacancy(@Param("vacancyId") Long vacancyId);

    @Query("select (count(a) > 0) from JobApplication a where a.resume.id = :resumeId")
    boolean hasApplicationsForResume(@Param("resumeId") Long resumeId);
}
