package com.example.jobsearch.category.application;

import com.example.jobsearch.category.domain.Category;
import com.example.jobsearch.category.web.CategoryRequest;
import com.example.jobsearch.category.web.CategoryResponse;

final class CategoryMapper {
    private CategoryMapper() {
    }

    static CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getParentId());
    }

    static Category toEntity(CategoryRequest request) {
        Category category = new Category();
        update(category, request);
        return category;
    }

    static void update(Category category, CategoryRequest request) {
        category.setName(request.name().trim());
        category.setParentId(request.parentId());
    }
}
