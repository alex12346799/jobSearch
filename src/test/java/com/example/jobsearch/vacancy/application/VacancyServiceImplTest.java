package com.example.jobsearch.vacancy.application;

import com.example.jobsearch.category.domain.Category;
import com.example.jobsearch.category.persistence.CategoryRepository;
import com.example.jobsearch.exceptions.ResourceNotFoundException;
import com.example.jobsearch.model.User;
import com.example.jobsearch.repository.UserRepository;
import com.example.jobsearch.vacancy.application.exception.VacancyAccessDeniedException;
import com.example.jobsearch.vacancy.application.exception.VacancyInUseException;
import com.example.jobsearch.vacancy.domain.Vacancy;
import com.example.jobsearch.vacancy.persistence.VacancyRepository;
import com.example.jobsearch.vacancy.web.VacancyRequest;
import com.example.jobsearch.vacancy.web.VacancyResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VacancyServiceImplTest {
    @Mock
    private VacancyRepository vacancyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private VacancyServiceImpl vacancyService;

    @Test
    void delegatesSearchAndCategoryFilterToDatabasePageQuery() {
        PageRequest pageable = PageRequest.of(0, 20);
        when(vacancyRepository.search("java", 2L, pageable)).thenReturn(new PageImpl<>(List.of(), pageable, 0));

        vacancyService.findAll(" java ", 2L, pageable);

        verify(vacancyRepository).search("java", 2L, pageable);
    }

    @Test
    void rejectsCreationWhenCategoryIsMissing() {
        Authentication authentication = authentication("owner@example.com", "EMPLOYEE");
        when(userRepository.findByEmail("owner@example.com")).thenReturn(Optional.of(user(7L, "owner@example.com")));
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> vacancyService.create(request(99L), authentication))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category with id 99 was not found");

        verify(vacancyRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void createsVacancyForCurrentAuthenticatedEmployerAndTrimsStrings() {
        Authentication authentication = authentication("owner@example.com", "EMPLOYEE");
        User owner = user(7L, "owner@example.com");
        Category category = category(1L, "IT");
        when(userRepository.findByEmail("owner@example.com")).thenReturn(Optional.of(owner));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(vacancyRepository.save(org.mockito.ArgumentMatchers.any(Vacancy.class)))
                .thenAnswer(invocation -> {
                    Vacancy vacancy = invocation.getArgument(0);
                    vacancy.setId(1L);
                    return vacancy;
                });

        VacancyResponse response = vacancyService.create(request(1L), authentication);

        ArgumentCaptor<Vacancy> captor = ArgumentCaptor.forClass(Vacancy.class);
        verify(vacancyRepository).save(captor.capture());
        assertThat(captor.getValue().getTitle()).isEqualTo("Java Developer");
        assertThat(captor.getValue().getDescription()).isEqualTo("Build services");
        assertThat(captor.getValue().getEmployer()).isSameAs(owner);
        assertThat(response.employerId()).isEqualTo(7L);
    }

    @Test
    void allowsOwnerToUpdateVacancy() {
        Authentication authentication = authentication("owner@example.com", "EMPLOYEE");
        User owner = user(7L, "owner@example.com");
        Vacancy vacancy = vacancy(1L, owner, category(1L, "IT"));
        Category newCategory = category(2L, "Backend");
        when(vacancyRepository.findById(1L)).thenReturn(Optional.of(vacancy));
        when(userRepository.findByEmail("owner@example.com")).thenReturn(Optional.of(owner));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(newCategory));
        when(vacancyRepository.save(vacancy)).thenReturn(vacancy);

        VacancyResponse response = vacancyService.update(1L, request(2L), authentication);

        assertThat(response.categoryId()).isEqualTo(2L);
        assertThat(vacancy.getEmployer()).isSameAs(owner);
    }

    @Test
    void forbidsAnotherEmployerFromUpdatingVacancy() {
        User owner = user(7L, "owner@example.com");
        User stranger = user(8L, "other@example.com");
        Vacancy vacancy = vacancy(1L, owner, category(1L, "IT"));
        when(vacancyRepository.findById(1L)).thenReturn(Optional.of(vacancy));
        when(userRepository.findByEmail("other@example.com")).thenReturn(Optional.of(stranger));

        assertThatThrownBy(() -> vacancyService.update(
                1L,
                request(1L),
                authentication("other@example.com", "EMPLOYEE")
        ))
                .isInstanceOf(VacancyAccessDeniedException.class)
                .hasMessage("You are not allowed to modify this vacancy");

        verify(vacancyRepository, never()).save(vacancy);
    }

    @Test
    void allowsAdministratorToUpdateAnyVacancy() {
        Vacancy vacancy = vacancy(1L, user(7L, "owner@example.com"), category(1L, "IT"));
        Category category = category(2L, "Backend");
        when(vacancyRepository.findById(1L)).thenReturn(Optional.of(vacancy));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));
        when(vacancyRepository.save(vacancy)).thenReturn(vacancy);

        vacancyService.update(1L, request(2L), authentication("admin@example.com", "ADMIN"));

        verify(vacancyRepository).save(vacancy);
        verify(userRepository, never()).findByEmail("admin@example.com");
    }

    @Test
    void deletesVacancyWithoutResponses() {
        User owner = user(7L, "owner@example.com");
        Vacancy vacancy = vacancy(1L, owner, category(1L, "IT"));
        when(vacancyRepository.findById(1L)).thenReturn(Optional.of(vacancy));
        when(userRepository.findByEmail("owner@example.com")).thenReturn(Optional.of(owner));

        vacancyService.delete(1L, authentication("owner@example.com", "EMPLOYEE"));

        verify(vacancyRepository).delete(vacancy);
    }

    @Test
    void rejectsDeletingVacancyWithResponses() {
        User owner = user(7L, "owner@example.com");
        Vacancy vacancy = vacancy(1L, owner, category(1L, "IT"));
        when(vacancyRepository.findById(1L)).thenReturn(Optional.of(vacancy));
        when(userRepository.findByEmail("owner@example.com")).thenReturn(Optional.of(owner));
        when(vacancyRepository.hasResponses(1L)).thenReturn(true);

        assertThatThrownBy(() -> vacancyService.delete(1L, authentication("owner@example.com", "EMPLOYEE")))
                .isInstanceOf(VacancyInUseException.class);

        verify(vacancyRepository, never()).delete(vacancy);
    }

    private VacancyRequest request(Long categoryId) {
        return new VacancyRequest(" Java Developer ", " Build services ", categoryId, 1000, 1, 3, true);
    }

    private Authentication authentication(String email, String authority) {
        return new TestingAuthenticationToken(email, "password", authority);
    }

    private User user(Long id, String email) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setName("Employer");
        return user;
    }

    private Category category(Long id, String name) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }

    private Vacancy vacancy(Long id, User owner, Category category) {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(id);
        vacancy.setTitle("Old title");
        vacancy.setDescription("Old description");
        vacancy.setCategory(category);
        vacancy.setEmployer(owner);
        vacancy.setSalary(500);
        vacancy.setActive(true);
        return vacancy;
    }
}
