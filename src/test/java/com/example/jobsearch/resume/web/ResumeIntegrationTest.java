package com.example.jobsearch.resume.web;

import com.example.jobsearch.category.domain.Category;
import com.example.jobsearch.category.persistence.CategoryRepository;
import com.example.jobsearch.resume.domain.Resume;
import com.example.jobsearch.resume.persistence.ResumeRepository;
import com.example.jobsearch.user.domain.*;
import com.example.jobsearch.user.persistence.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.jpa.properties.hibernate.generate_statistics=true")
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ResumeIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private JwtEncoder jwtEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ResumeRepository resumeRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private EntityManager entityManager;
    @Autowired private EntityManagerFactory entityManagerFactory;
    @Autowired private JdbcTemplate jdbcTemplate;
    private User owner;
    private User other;
    private User employer;
    private Category category;

    @BeforeEach
    void setUp() {
        owner = user("resume.owner@example.com", RoleName.APPLICANT);
        other = user("resume.other@example.com", RoleName.APPLICANT);
        employer = user("resume.employer@example.com", RoleName.EMPLOYER);
        category = new Category(); category.setName("Resume Test " + UUID.randomUUID().toString().substring(0, 8));
        category = categoryRepository.saveAndFlush(category);
    }

    @Test
    void createsAndReturnsNestedResumeAndMine() throws Exception {
        long id = create(owner, "Java Engineer", category.getId());
        mockMvc.perform(get("/api/v1/resumes/{id}", id).header("Authorization", bearer(owner)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Java Engineer"))
                .andExpect(jsonPath("$.education[0].institution").value("University"))
                .andExpect(jsonPath("$.workExperience[0].companyName").value("Company"))
                .andExpect(jsonPath("$.socialLinks.telegram").value("telegram"))
                .andExpect(jsonPath("$.password").doesNotExist());
        mockMvc.perform(get("/api/v1/resumes/me").header("Authorization", bearer(owner)))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id));
    }

    @Test
    void pagesSearchesFiltersAndUsesBoundedQueries() throws Exception {
        create(owner, "Unique Java Resume", category.getId());
        create(owner, "Unique Java Architect", category.getId());
        create(owner, "Unique Java Developer", category.getId());
        entityManager.flush(); entityManager.clear();
        var statistics = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
        statistics.clear();
        mockMvc.perform(get("/api/v1/resumes").param("search", "Unique Java")
                        .param("categoryId", category.getId().toString()).param("size", "5")
                        .header("Authorization", bearer(employer)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.totalElements").value(3));
        assertThat(statistics.getPrepareStatementCount()).isLessThanOrEqualTo(4);
    }

    @Test
    void missingCategoryAndValidationAreRejected() throws Exception {
        mockMvc.perform(post("/api/v1/resumes").header("Authorization", bearer(owner))
                        .contentType("application/json").content(body("Resume", 999999L)))
                .andExpect(status().isNotFound());
        mockMvc.perform(post("/api/v1/resumes").header("Authorization", bearer(owner))
                        .contentType("application/json").content(body("   ", category.getId())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ownerUpdatesAndOtherApplicantIsForbidden() throws Exception {
        long id = create(owner, "Before", category.getId());
        mockMvc.perform(put("/api/v1/resumes/{id}", id).header("Authorization", bearer(owner))
                        .contentType("application/json").content(body("After", category.getId())))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("After"));
        mockMvc.perform(put("/api/v1/resumes/{id}", id).header("Authorization", bearer(other))
                        .contentType("application/json").content(body("Stolen", category.getId())))
                .andExpect(status().isForbidden());
    }

    @Test
    void employerCanViewButCannotCreateOrModify() throws Exception {
        long id = create(owner, "Visible", category.getId());
        mockMvc.perform(get("/api/v1/resumes/{id}", id).header("Authorization", bearer(employer)))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/v1/resumes").header("Authorization", bearer(employer))
                        .contentType("application/json").content(body("Forbidden", category.getId())))
                .andExpect(status().isForbidden());
        mockMvc.perform(put("/api/v1/resumes/{id}", id).header("Authorization", bearer(employer))
                        .contentType("application/json").content(body("Forbidden", category.getId())))
                .andExpect(status().isForbidden());
    }

    @Test
    void ownerDeletesResumeAndOwnedChildren() throws Exception {
        long id = create(owner, "Delete me", category.getId());
        mockMvc.perform(delete("/api/v1/resumes/{id}", id).header("Authorization", bearer(owner)))
                .andExpect(status().isNoContent());
        entityManager.flush(); entityManager.clear();
        assertThat(resumeRepository.findById(id)).isEmpty();
    }

    @Test
    void resumeWithApplicationCannotBeDeleted() throws Exception {
        long id = create(owner, "In use", category.getId());
        Long vacancyId = jdbcTemplate.queryForObject("select min(id) from vacancy", Long.class);
        jdbcTemplate.update("insert into respondent_applicant(resume_id, vacancy_id, status, create_date, update_date) values (?, ?, 'CREATED', current_timestamp, current_timestamp)", id, vacancyId);

        mockMvc.perform(delete("/api/v1/resumes/{id}", id).header("Authorization", bearer(owner)))
                .andExpect(status().isConflict());
        assertThat(resumeRepository.findById(id)).isPresent();
    }

    private long create(User applicant, String name, long categoryId) throws Exception {
        String response = mockMvc.perform(post("/api/v1/resumes").header("Authorization", bearer(applicant))
                        .contentType("application/json").content(body(name, categoryId)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        return new com.fasterxml.jackson.databind.ObjectMapper().readTree(response).get("id").asLong();
    }

    private String body(String name, long categoryId) {
        return """
                {"name":"%s","categoryId":%d,"salary":1000,"active":true,
                 "education":[{"institution":"University","program":"CS","startDate":"2020-01-01","endDate":"2021-01-01","degree":"Bachelor"}],
                 "workExperience":[{"startDate":"2021-01-02","endDate":"2022-01-01","companyName":"Company","position":"Developer","responsibilities":"Code"}],
                 "socialLinks":{"telegram":"telegram","facebook":"facebook","linkedin":"linkedin"}}
                """.formatted(name, categoryId);
    }

    private User user(String email, RoleName roleName) {
        Role role = roleRepository.findByName(roleName).orElseThrow();
        User user = new User(); user.setName("Resume"); user.setSurname("User"); user.setEmail(email);
        user.setPassword(passwordEncoder.encode("password")); user.setRole(role); user.setEnabled(true);
        return userRepository.saveAndFlush(user);
    }

    private String bearer(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder().subject(user.getId().toString()).claim("email", user.getEmail())
                .claim("role", user.getRole().getName().name()).issuedAt(now).expiresAt(now.plusSeconds(300))
                .id(UUID.randomUUID().toString()).build();
        return "Bearer " + jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
    }
}
