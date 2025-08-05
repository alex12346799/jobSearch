package com.example.jobsearch.mapper;

import com.example.jobsearch.dto.CategoryRequestDto;
import com.example.jobsearch.dto.CategoryResponseDto;
import com.example.jobsearch.model.Category;

public class CategoryMapper {
    public static CategoryResponseDto toDto(Category category) {
        if(category == null) return null;
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }
    public static Category fromDto(CategoryRequestDto dto) {
        if(dto == null) return null;
        Category category = new Category();
        category.setName(dto.getName());
        category.setParentId(dto.getParentId());
        return category;
    }
}


