package com.example.jobsearch.repository;

import com.example.jobsearch.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c.name FROM Category c WHERE c.id = :id")
    String findNameById(@Param("id") Long id);
}
