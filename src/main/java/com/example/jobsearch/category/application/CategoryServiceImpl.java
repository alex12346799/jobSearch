package com.example.jobsearch.category.application;

import com.example.jobsearch.category.application.exception.CategoryHierarchyConflictException;
import com.example.jobsearch.category.application.exception.CategoryInUseException;
import com.example.jobsearch.category.application.exception.CategoryNameConflictException;
import com.example.jobsearch.category.application.exception.CategoryValidationException;
import com.example.jobsearch.category.domain.Category;
import com.example.jobsearch.category.persistence.CategoryRepository;
import com.example.jobsearch.category.web.CategoryRequest;
import com.example.jobsearch.category.web.CategoryResponse;
import com.example.jobsearch.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        String name = normalizeName(request.name());
        validateUniqueName(name, null);
        validateParent(request.parentId(), null);
        CategoryRequest normalizedRequest = new CategoryRequest(name, request.parentId());
        return CategoryMapper.toResponse(categoryRepository.save(CategoryMapper.toEntity(normalizedRequest)));
    }

    @Override
    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = getCategory(id);
        String name = normalizeName(request.name());
        validateUniqueName(name, id);
        validateParent(request.parentId(), id);
        CategoryMapper.update(category, new CategoryRequest(name, request.parentId()));
        return CategoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Category category = getCategory(id);
        if (categoryRepository.existsByParentId(id)
                || categoryRepository.isUsedByVacancy(id)
                || categoryRepository.isUsedByResume(id)) {
            throw new CategoryInUseException(id);
        }
        categoryRepository.delete(category);
    }

    private String normalizeName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new CategoryValidationException("Category name must not be blank");
        }
        return name.trim();
    }

    private void validateUniqueName(String name, Long categoryId) {
        boolean duplicate = categoryId == null
                ? categoryRepository.existsByName(name)
                : categoryRepository.existsByNameAndIdNot(name, categoryId);
        if (duplicate) {
            throw new CategoryNameConflictException(name);
        }
    }

    private void validateParent(Long parentId, Long categoryId) {
        if (parentId == null) {
            return;
        }
        if (parentId.equals(categoryId)) {
            throw new CategoryHierarchyConflictException("A category cannot be its own parent");
        }

        Category parent = getCategory(parentId);
        Set<Long> visited = new HashSet<>();
        while (parent != null) {
            if (!visited.add(parent.getId())) {
                throw new CategoryHierarchyConflictException("Category hierarchy contains a cycle");
            }
            if (parent.getId().equals(categoryId)) {
                throw new CategoryHierarchyConflictException("Category hierarchy would contain a cycle");
            }
            parent = parent.getParentId() == null ? null : getCategory(parent.getParentId());
        }
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " was not found"));
    }
}
