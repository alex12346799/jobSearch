package com.example.jobsearch.vacancy.web;

import com.example.jobsearch.config.SecurityConfig;
import com.example.jobsearch.exceptions.ResourceNotFoundException;
import com.example.jobsearch.auth.security.RestAccessDeniedHandler;
import com.example.jobsearch.auth.security.RestAuthenticationEntryPoint;
import com.example.jobsearch.vacancy.application.VacancyService;
import com.example.jobsearch.vacancy.application.exception.VacancyAccessDeniedException;
import com.example.jobsearch.vacancy.application.exception.VacancyInUseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VacancyController.class)
@Import({VacancyControllerTest.TestConfig.class, SecurityConfig.class,
        RestAuthenticationEntryPoint.class, RestAccessDeniedHandler.class})
@WithMockUser(username = "owner@example.com", authorities = "EMPLOYER")
class VacancyControllerTest {
    private static final String BASE_PATH = "/api/v1/vacancies";
    private static final String VALID_REQUEST = """
            {
              "title":"Java Developer",
              "description":"Build backend services",
              "categoryId":1,
              "salary":1000,
              "expFrom":1,
              "expTo":3,
              "isActive":true
            }
            """;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StubVacancyService vacancyService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @BeforeEach
    void resetService() {
        vacancyService.reset();
    }

    @Test
    void returnsVacancyPage() throws Exception {
        vacancyService.page = new PageImpl<>(List.of(response()), PageRequest.of(0, 10), 1);

        mockMvc.perform(get(BASE_PATH).param("page", "0").param("size", "10").param("sort", "title,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Java Developer"))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void filtersByCategory() throws Exception {
        mockMvc.perform(get(BASE_PATH).param("categoryId", "2"))
                .andExpect(status().isOk());

        assertThat(vacancyService.categoryId).isEqualTo(2L);
    }

    @Test
    void searchesByText() throws Exception {
        mockMvc.perform(get(BASE_PATH).param("search", "java"))
                .andExpect(status().isOk());

        assertThat(vacancyService.search).isEqualTo("java");
    }

    @Test
    void returnsVacancyById() throws Exception {
        vacancyService.response = response();

        mockMvc.perform(get(BASE_PATH + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.categoryName").value("IT"))
                .andExpect(jsonPath("$.employerId").value(7));
    }

    @Test
    void createsValidVacancy() throws Exception {
        vacancyService.response = response();

        mockMvc.perform(post(BASE_PATH).with(csrf()).contentType("application/json").content(VALID_REQUEST))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/vacancies/1"))
                .andExpect(jsonPath("$.title").value("Java Developer"));
    }

    @Test
    @WithMockUser(username = "admin@example.com", authorities = "ADMIN")
    void createsValidVacancyAsAdministrator() throws Exception {
        vacancyService.response = response();

        mockMvc.perform(post(BASE_PATH).with(csrf()).contentType("application/json").content(VALID_REQUEST))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "APPLICANT")
    void forbidsApplicantFromCreatingVacancy() throws Exception {
        mockMvc.perform(post(BASE_PATH).with(csrf()).contentType("application/json").content(VALID_REQUEST))
                .andExpect(status().isForbidden());
    }

    @Test
    void rejectsInvalidVacancy() throws Exception {
        mockMvc.perform(post(BASE_PATH)
                        .with(csrf())
                        .contentType("application/json")
                        .content("""
                                {"title":" ","description":"","categoryId":0,"salary":0,"isActive":null}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.title").exists())
                .andExpect(jsonPath("$.validationErrors.description").exists())
                .andExpect(jsonPath("$.validationErrors.categoryId").exists())
                .andExpect(jsonPath("$.validationErrors.salary").exists())
                .andExpect(jsonPath("$.validationErrors.isActive").exists());
    }

    @Test
    void returnsNotFoundWhenCategoryIsMissing() throws Exception {
        vacancyService.failure = new ResourceNotFoundException("Category with id 99 was not found");

        mockMvc.perform(post(BASE_PATH)
                        .with(csrf())
                        .contentType("application/json")
                        .content(VALID_REQUEST.replace("\"categoryId\":1", "\"categoryId\":99")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Category with id 99 was not found"));
    }

    @Test
    void updatesVacancyAsOwner() throws Exception {
        vacancyService.response = response();

        mockMvc.perform(put(BASE_PATH + "/1").with(csrf()).contentType("application/json").content(VALID_REQUEST))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void forbidsUpdatingAnotherEmployersVacancy() throws Exception {
        vacancyService.failure = new VacancyAccessDeniedException("You are not allowed to modify this vacancy");

        mockMvc.perform(put(BASE_PATH + "/1").with(csrf()).contentType("application/json").content(VALID_REQUEST))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("You are not allowed to modify this vacancy"));
    }

    @Test
    void deletesVacancy() throws Exception {
        mockMvc.perform(delete(BASE_PATH + "/1").with(csrf()))
                .andExpect(status().isNoContent());

        assertThat(vacancyService.deletedId).isEqualTo(1L);
    }

    @Test
    void returnsConflictWhenVacancyHasResponses() throws Exception {
        vacancyService.failure = new VacancyInUseException(1L);

        mockMvc.perform(delete(BASE_PATH + "/1").with(csrf()))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Vacancy with id 1 has responses and cannot be deleted"));
    }

    private VacancyResponse response() {
        return new VacancyResponse(
                1L, "Java Developer", "Build backend services", 1L, "IT", new java.math.BigDecimal("1000"),
                1, 3, true, 7L, "Employer", LocalDateTime.now(), LocalDateTime.now()
        );
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        StubVacancyService vacancyService() {
            return new StubVacancyService();
        }
    }

    static class StubVacancyService implements VacancyService {
        private Page<VacancyResponse> page;
        private VacancyResponse response;
        private RuntimeException failure;
        private String search;
        private Long categoryId;
        private Long deletedId;

        void reset() {
            page = Page.empty();
            response = null;
            failure = null;
            search = null;
            categoryId = null;
            deletedId = null;
        }

        @Override
        public Page<VacancyResponse> findAll(String search, Long categoryId, Pageable pageable) {
            this.search = search;
            this.categoryId = categoryId;
            return page;
        }

        @Override
        public VacancyResponse findById(Long id) {
            throwIfConfigured();
            return response;
        }

        @Override
        public VacancyResponse create(VacancyRequest request, Authentication authentication) {
            throwIfConfigured();
            return response;
        }

        @Override
        public VacancyResponse update(Long id, VacancyRequest request, Authentication authentication) {
            throwIfConfigured();
            return response;
        }

        @Override
        public void delete(Long id, Authentication authentication) {
            throwIfConfigured();
            deletedId = id;
        }

        @Override
        public List<VacancyResponse> findByEmployer(Authentication authentication) {
            return List.of();
        }

        private void throwIfConfigured() {
            if (failure != null) {
                throw failure;
            }
        }
    }
}
