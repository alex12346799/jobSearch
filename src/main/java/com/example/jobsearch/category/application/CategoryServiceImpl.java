package com.example.jobsearch.category.application;

import com.example.jobsearch.category.domain.Category;
import com.example.jobsearch.category.persistence.CategoryRepository;
import com.example.jobsearch.category.web.CategoryRequest;
import com.example.jobsearch.category.web.CategoryResponse;
import com.example.jobsearch.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        return CategoryMapper.toResponse(getCategory(id));
    }

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        return CategoryMapper.toResponse(categoryRepository.save(CategoryMapper.toEntity(request)));
    }

    @Override
    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = getCategory(id);
        CategoryMapper.update(category, request);
        return CategoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        categoryRepository.delete(getCategory(id));
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " was not found"));
    }
}
