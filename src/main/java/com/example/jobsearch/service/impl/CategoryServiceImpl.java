package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.impl.CategoryDao;
import com.example.jobsearch.model.Category;
import com.example.jobsearch.repository.CategoryRepository;
import com.example.jobsearch.service.CategoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
