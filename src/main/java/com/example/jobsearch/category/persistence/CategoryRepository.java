package com.example.jobsearch.category.persistence;

import com.example.jobsearch.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c.name FROM Category c WHERE c.id = :id")
    String findNameById(@Param("id") Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByParentId(Long parentId);

    @Query("SELECT (COUNT(v) > 0) FROM Vacancy v WHERE v.category.id = :categoryId")
    boolean isUsedByVacancy(@Param("categoryId") Long categoryId);

    @Query("SELECT (COUNT(r) > 0) FROM Resume r WHERE r.category.id = :categoryId")
    boolean isUsedByResume(@Param("categoryId") Long categoryId);
}
