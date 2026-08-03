package com.example.jobsearch.vacancy.persistence;

import com.example.jobsearch.vacancy.domain.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    @Query(
            value = """
                    SELECT v FROM Vacancy v
                    JOIN FETCH v.category c
                    JOIN FETCH v.employer e
                    WHERE (:categoryId IS NULL OR c.id = :categoryId)
                      AND (:search = '' OR LOWER(v.title) LIKE LOWER(CONCAT('%', :search, '%'))
                           OR LOWER(v.description) LIKE LOWER(CONCAT('%', :search, '%')))
                    """,
            countQuery = """
                    SELECT COUNT(v) FROM Vacancy v
                    WHERE (:categoryId IS NULL OR v.category.id = :categoryId)
                      AND (:search = '' OR LOWER(v.title) LIKE LOWER(CONCAT('%', :search, '%'))
                           OR LOWER(v.description) LIKE LOWER(CONCAT('%', :search, '%')))
                    """
    )
    Page<Vacancy> search(
            @Param("search") String search,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );

    @Override
    @EntityGraph(attributePaths = {"category", "employer"})
    Optional<Vacancy> findById(Long id);

    @EntityGraph(attributePaths = {"category", "employer"})
    List<Vacancy> findByEmployerEmailOrderByIdDesc(String email);

    @Query("SELECT (COUNT(r) > 0) FROM JobApplication r WHERE r.vacancy.id = :vacancyId")
    boolean hasResponses(@Param("vacancyId") Long vacancyId);
}
