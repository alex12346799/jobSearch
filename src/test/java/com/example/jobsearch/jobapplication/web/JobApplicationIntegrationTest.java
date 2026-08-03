package com.example.jobsearch.jobapplication.web;

import com.example.jobsearch.category.domain.Category;
import com.example.jobsearch.category.persistence.CategoryRepository;
import com.example.jobsearch.jobapplication.domain.JobApplicationStatus;
import com.example.jobsearch.jobapplication.persistence.JobApplicationRepository;
import com.example.jobsearch.resume.domain.Resume;
import com.example.jobsearch.resume.persistence.ResumeRepository;
import com.example.jobsearch.user.domain.*;
import com.example.jobsearch.user.persistence.*;
import com.example.jobsearch.vacancy.domain.Vacancy;
import com.example.jobsearch.vacancy.persistence.VacancyRepository;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.jpa.properties.hibernate.generate_statistics=true")
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class JobApplicationIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private JwtEncoder jwtEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ResumeRepository resumeRepository;
    @Autowired private VacancyRepository vacancyRepository;
    @Autowired private JobApplicationRepository applicationRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private EntityManager entityManager;
    @Autowired private EntityManagerFactory entityManagerFactory;

    private User applicant;
    private User otherApplicant;
    private User employer;
    private User otherEmployer;
    private Category category;
    private Resume resume;
    private Vacancy vacancy;

    @BeforeEach
    void setUp() {
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        applicant = user("applicant." + suffix + "@example.com", RoleName.APPLICANT);
        otherApplicant = user("other.applicant." + suffix + "@example.com", RoleName.APPLICANT);
        employer = user("employer." + suffix + "@example.com", RoleName.EMPLOYER);
        otherEmployer = user("other.employer." + suffix + "@example.com", RoleName.EMPLOYER);
        category = new Category(); category.setName("Applications " + suffix);
        category = categoryRepository.saveAndFlush(category);
        resume = resume(applicant, true, "Applicant resume");
        vacancy = vacancy(employer, true, "Backend engineer");
    }

    @Test
    void applicantCreatesApplicationAndDuplicateIsRejected() throws Exception {
        long id = create(applicant, vacancy, resume);
        mockMvc.perform(post("/api/v1/job-applications").header("Authorization", bearer(applicant))
                        .contentType("application/json").content(request(vacancy, resume)))
                .andExpect(status().isConflict());
        assertThat(applicationRepository.findById(id)).get()
                .extracting("status").isEqualTo(JobApplicationStatus.CREATED);
    }

    @Test
    void employerCannotCreateAndForeignResumeIsForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/job-applications").header("Authorization", bearer(employer))
                        .contentType("application/json").content(request(vacancy, resume)))
                .andExpect(status().isForbidden());
        mockMvc.perform(post("/api/v1/job-applications").header("Authorization", bearer(otherApplicant))
                        .contentType("application/json").content(request(vacancy, resume)))
                .andExpect(status().isForbidden());
    }

    @Test
    void missingAndInactiveVacancyAndInactiveResumeAreRejected() throws Exception {
        mockMvc.perform(post("/api/v1/job-applications").header("Authorization", bearer(applicant))
                        .contentType("application/json").content("{\"vacancyId\":999999,\"resumeId\":" + resume.getId() + "}"))
                .andExpect(status().isNotFound());
        vacancy.setActive(false); vacancyRepository.saveAndFlush(vacancy);
        mockMvc.perform(post("/api/v1/job-applications").header("Authorization", bearer(applicant))
                        .contentType("application/json").content(request(vacancy, resume)))
                .andExpect(status().isConflict());
        vacancy.setActive(true); resume.setActive(false);
        vacancyRepository.saveAndFlush(vacancy); resumeRepository.saveAndFlush(resume);
        mockMvc.perform(post("/api/v1/job-applications").header("Authorization", bearer(applicant))
                        .contentType("application/json").content(request(vacancy, resume)))
                .andExpect(status().isConflict());
    }

    @Test
    void applicantSeesOwnApplicationsAndEmployerOnlyOwnVacancy() throws Exception {
        long id = create(applicant, vacancy, resume);
        mockMvc.perform(get("/api/v1/job-applications/me").header("Authorization", bearer(applicant)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.content[0].id").value(id));
        mockMvc.perform(get("/api/v1/vacancies/{id}/applications", vacancy.getId())
                        .header("Authorization", bearer(employer)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.content[0].applicant.id").value(applicant.getId()));
        mockMvc.perform(get("/api/v1/vacancies/{id}/applications", vacancy.getId())
                        .header("Authorization", bearer(otherEmployer)))
                .andExpect(status().isForbidden());
        mockMvc.perform(get("/api/v1/job-applications/{id}", id).header("Authorization", bearer(otherApplicant)))
                .andExpect(status().isForbidden());
    }

    @Test
    void vacancyOwnerUpdatesStatusAndFinalTransitionIsRejected() throws Exception {
        long id = create(applicant, vacancy, resume);
        mockMvc.perform(patch("/api/v1/job-applications/{id}/status", id)
                        .header("Authorization", bearer(employer)).contentType("application/json")
                        .content("{\"status\":\"REVIEWED\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("REVIEWED"));
        mockMvc.perform(patch("/api/v1/job-applications/{id}/status", id)
                        .header("Authorization", bearer(employer)).contentType("application/json")
                        .content("{\"status\":\"ACCEPTED\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("ACCEPTED"));
        mockMvc.perform(patch("/api/v1/job-applications/{id}/status", id)
                        .header("Authorization", bearer(employer)).contentType("application/json")
                        .content("{\"status\":\"REJECTED\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    void applicantWithdrawsOwnApplication() throws Exception {
        long id = create(applicant, vacancy, resume);
        mockMvc.perform(delete("/api/v1/job-applications/{id}", id).header("Authorization", bearer(applicant)))
                .andExpect(status().isNoContent());
        entityManager.flush(); entityManager.clear();
        assertThat(applicationRepository.findById(id)).get()
                .extracting("status").isEqualTo(JobApplicationStatus.WITHDRAWN);
    }

    @Test
    void applicationListsUseBoundedQueries() throws Exception {
        create(applicant, vacancy, resume);
        create(applicant, vacancy(employer, true, "Second"), resume(applicant, true, "Second resume"));
        create(applicant, vacancy(employer, true, "Third"), resume(applicant, true, "Third resume"));
        entityManager.flush(); entityManager.clear();
        var statistics = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
        statistics.clear();
        mockMvc.perform(get("/api/v1/job-applications/me").param("size", "10")
                        .header("Authorization", bearer(applicant)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.totalElements").value(3));
        assertThat(statistics.getPrepareStatementCount()).isLessThanOrEqualTo(5);
    }

    private long create(User user, Vacancy targetVacancy, Resume targetResume) throws Exception {
        String response = mockMvc.perform(post("/api/v1/job-applications").header("Authorization", bearer(user))
                        .contentType("application/json").content(request(targetVacancy, targetResume)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.status").value("CREATED"))
                .andReturn().getResponse().getContentAsString();
        return new com.fasterxml.jackson.databind.ObjectMapper().readTree(response).get("id").asLong();
    }

    private String request(Vacancy targetVacancy, Resume targetResume) {
        return "{\"vacancyId\":" + targetVacancy.getId() + ",\"resumeId\":" + targetResume.getId() + "}";
    }

    private Resume resume(User owner, boolean active, String name) {
        Resume value = new Resume(); value.setApplicant(owner); value.setCategory(category); value.setName(name);
        value.setSalary(1000); value.setActive(active); value.setCreatedDate(LocalDateTime.now());
        value.setUpdateDate(LocalDateTime.now()); return resumeRepository.saveAndFlush(value);
    }

    private Vacancy vacancy(User owner, boolean active, String title) {
        Vacancy value = new Vacancy(); value.setEmployer(owner); value.setCategory(category); value.setTitle(title);
        value.setDescription("Description"); value.setSalary(1000); value.setExpFrom(0); value.setExpTo(2);
        value.setActive(active); return vacancyRepository.saveAndFlush(value);
    }

    private User user(String email, RoleName roleName) {
        Role role = roleRepository.findByName(roleName).orElseThrow();
        User value = new User(); value.setName("Test"); value.setSurname("User"); value.setEmail(email);
        value.setPassword(passwordEncoder.encode("password")); value.setRole(role); value.setEnabled(true);
        return userRepository.saveAndFlush(value);
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
