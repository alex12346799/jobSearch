package com.example.jobsearch.resume.persistence;

import com.example.jobsearch.resume.domain.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query(value = """
            select r from Resume r join fetch r.category c join fetch r.applicant a left join fetch r.socialLinks
            where (:activeOnly = false or r.active = true)
              and (:categoryId is null or c.id = :categoryId)
              and (:search = '' or lower(r.name) like lower(concat('%', :search, '%')))
            """, countQuery = """
            select count(r) from Resume r
            where (:activeOnly = false or r.active = true)
              and (:categoryId is null or r.category.id = :categoryId)
              and (:search = '' or lower(r.name) like lower(concat('%', :search, '%')))
            """)
    Page<Resume> search(@Param("search") String search, @Param("categoryId") Long categoryId,
                        @Param("activeOnly") boolean activeOnly, Pageable pageable);

    @EntityGraph(attributePaths = {"applicant", "category", "educationInfo", "workExperienceInfo"})
    @Query("select distinct r from Resume r left join fetch r.socialLinks where r.id in :ids")
    List<Resume> findDetailedByIdIn(@Param("ids") List<Long> ids);

    @EntityGraph(attributePaths = {"applicant", "category", "educationInfo", "workExperienceInfo"})
    @Query("select r from Resume r left join fetch r.socialLinks where r.id = :id")
    Optional<Resume> findDetailedById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"applicant", "category", "educationInfo", "workExperienceInfo"})
    @Query("select distinct r from Resume r left join fetch r.socialLinks where r.applicant.id = :applicantId order by r.id desc")
    List<Resume> findByApplicantIdOrderByIdDesc(@Param("applicantId") Long applicantId);

    @Query("select (count(a) > 0) from JobApplication a where a.resume.id = :resumeId")
    boolean hasApplications(@Param("resumeId") Long resumeId);
}
