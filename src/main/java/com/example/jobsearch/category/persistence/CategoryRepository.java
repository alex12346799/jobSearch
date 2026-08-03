package com.example.jobsearch.category.persistence;

import com.example.jobsearch.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c.name FROM Category c WHERE c.id = :id")
    String findNameById(@Param("id") Long id);
}
