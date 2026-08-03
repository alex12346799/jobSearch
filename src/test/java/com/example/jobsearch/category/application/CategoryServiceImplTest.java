package com.example.jobsearch.category.application;

import com.example.jobsearch.category.application.exception.CategoryHierarchyConflictException;
import com.example.jobsearch.category.application.exception.CategoryInUseException;
import com.example.jobsearch.category.application.exception.CategoryNameConflictException;
import com.example.jobsearch.category.domain.Category;
import com.example.jobsearch.category.persistence.CategoryRepository;
import com.example.jobsearch.category.web.CategoryRequest;
import com.example.jobsearch.category.web.CategoryResponse;
import com.example.jobsearch.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void rejectsDuplicateName() {
        when(categoryRepository.existsByName("Backend")).thenReturn(true);

        assertThatThrownBy(() -> categoryService.create(new CategoryRequest(" Backend ", null)))
                .isInstanceOf(CategoryNameConflictException.class)
                .hasMessage("Category name 'Backend' is already in use");

        verify(categoryRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void rejectsMissingParent() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.create(new CategoryRequest("Backend", 99L)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category with id 99 was not found");
    }

    @Test
    void rejectsCategoryAsItsOwnParent() {
        Category category = category(2L, "Backend", null);
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));

        assertThatThrownBy(() -> categoryService.update(2L, new CategoryRequest("Backend", 2L)))
                .isInstanceOf(CategoryHierarchyConflictException.class)
                .hasMessage("A category cannot be its own parent");
    }

    @Test
    void rejectsCycleAtAnyDepth() {
        Category root = category(1L, "Root", null);
        Category middle = category(2L, "Middle", 1L);
        Category leaf = category(3L, "Leaf", 2L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(root));
        when(categoryRepository.findById(3L)).thenReturn(Optional.of(leaf));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(middle));

        assertThatThrownBy(() -> categoryService.update(1L, new CategoryRequest("Root", 3L)))
                .isInstanceOf(CategoryHierarchyConflictException.class)
                .hasMessage("Category hierarchy would contain a cycle");
    }

    @Test
    void rejectsDeletingCategoryWithChildren() {
        Category category = category(1L, "Root", null);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.existsByParentId(1L)).thenReturn(true);

        assertThatThrownBy(() -> categoryService.delete(1L))
                .isInstanceOf(CategoryInUseException.class);

        verify(categoryRepository, never()).delete(category);
    }

    @Test
    void rejectsDeletingCategoryUsedByVacancy() {
        Category category = category(1L, "IT", null);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.isUsedByVacancy(1L)).thenReturn(true);

        assertThatThrownBy(() -> categoryService.delete(1L))
                .isInstanceOf(CategoryInUseException.class);

        verify(categoryRepository, never()).delete(category);
    }

    @Test
    void rejectsDeletingCategoryUsedByResume() {
        Category category = category(1L, "IT", null);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.isUsedByResume(1L)).thenReturn(true);

        assertThatThrownBy(() -> categoryService.delete(1L))
                .isInstanceOf(CategoryInUseException.class);

        verify(categoryRepository, never()).delete(category);
    }

    @Test
    void createsTrimmedChildCategory() {
        Category parent = category(1L, "IT", null);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(parent));
        when(categoryRepository.save(org.mockito.ArgumentMatchers.any(Category.class)))
                .thenAnswer(invocation -> {
                    Category saved = invocation.getArgument(0);
                    saved.setId(2L);
                    return saved;
                });

        CategoryResponse response = categoryService.create(new CategoryRequest(" Backend ", 1L));

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("Backend");
        assertThat(captor.getValue().getParentId()).isEqualTo(1L);
        assertThat(response).isEqualTo(new CategoryResponse(2L, "Backend", 1L));
    }

    private Category category(Long id, String name, Long parentId) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setParentId(parentId);
        return category;
    }
}
