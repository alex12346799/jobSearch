package com.example.jobsearch.category.web;

import com.example.jobsearch.category.application.CategoryService;
import com.example.jobsearch.config.SecurityConfig;
import com.example.jobsearch.exceptions.ResourceNotFoundException;
import com.example.jobsearch.security.CustomAuthenticationSuccessHandler;
import com.example.jobsearch.security.CustomUserDetailsService;
import com.example.jobsearch.utils.RedirectHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@Import({CategoryControllerTest.TestConfig.class, SecurityConfig.class})
@WithMockUser
class CategoryControllerTest {
    private static final String BASE_PATH = "/api/v1/categories";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StubCategoryService categoryService;

    @MockitoBean
    private RedirectHelper redirectHelper;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @BeforeEach
    void resetService() {
        categoryService.reset();
    }

    @Test
    void returnsCategoryList() throws Exception {
        categoryService.categories = List.of(
                new CategoryResponse(1L, "IT", null),
                new CategoryResponse(2L, "Backend", 1L)
        );

        mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("IT"))
                .andExpect(jsonPath("$[1].parentId").value(1));
    }

    @Test
    void createsCategory() throws Exception {
        categoryService.created = new CategoryResponse(3L, "QA", 1L);

        mockMvc.perform(post(BASE_PATH)
                        .with(csrf())
                        .contentType("application/json")
                        .content("""
                                {"name":"QA","parentId":1}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/categories/3"))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("QA"));
    }

    @Test
    void rejectsInvalidCategory() throws Exception {
        mockMvc.perform(post(BASE_PATH)
                        .with(csrf())
                        .contentType("application/json")
                        .content("""
                                {"name":" ","parentId":0}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.path").value(BASE_PATH))
                .andExpect(jsonPath("$.validationErrors.name").exists())
                .andExpect(jsonPath("$.validationErrors.parentId").exists());
    }

    @Test
    void returnsNotFoundForMissingCategory() throws Exception {
        categoryService.notFoundId = 99L;

        mockMvc.perform(get(BASE_PATH + "/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Category with id 99 was not found"))
                .andExpect(jsonPath("$.path").value(BASE_PATH + "/99"));
    }

    @Test
    void updatesCategory() throws Exception {
        categoryService.updated = new CategoryResponse(2L, "Platform", 1L);

        mockMvc.perform(put(BASE_PATH + "/2")
                        .with(csrf())
                        .contentType("application/json")
                        .content("""
                                {"name":"Platform","parentId":1}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Platform"))
                .andExpect(jsonPath("$.parentId").value(1));
    }

    @Test
    void deletesCategory() throws Exception {
        mockMvc.perform(delete(BASE_PATH + "/2").with(csrf()))
                .andExpect(status().isNoContent());

        org.assertj.core.api.Assertions.assertThat(categoryService.deletedId).isEqualTo(2L);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        StubCategoryService categoryService() {
            return new StubCategoryService();
        }
    }

    static class StubCategoryService implements CategoryService {
        private List<CategoryResponse> categories;
        private CategoryResponse created;
        private CategoryResponse updated;
        private Long notFoundId;
        private Long deletedId;

        void reset() {
            categories = Collections.emptyList();
            created = null;
            updated = null;
            notFoundId = null;
            deletedId = null;
        }

        @Override
        public List<CategoryResponse> findAll() {
            return categories;
        }

        @Override
        public CategoryResponse findById(Long id) {
            if (id.equals(notFoundId)) {
                throw new ResourceNotFoundException("Category with id " + id + " was not found");
            }
            return categories.stream().filter(category -> category.id().equals(id)).findFirst().orElse(null);
        }

        @Override
        public CategoryResponse create(CategoryRequest request) {
            return created;
        }

        @Override
        public CategoryResponse update(Long id, CategoryRequest request) {
            return updated;
        }

        @Override
        public void delete(Long id) {
            deletedId = id;
        }
    }
}
