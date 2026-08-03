package com.example.jobsearch.category.application;

import com.example.jobsearch.category.web.CategoryRequest;
import com.example.jobsearch.category.web.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findAll();

    CategoryResponse findById(Long id);

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(Long id, CategoryRequest request);

    void delete(Long id);
}
