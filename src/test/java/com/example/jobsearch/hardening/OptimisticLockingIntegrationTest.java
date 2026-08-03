package com.example.jobsearch.hardening;

import com.example.jobsearch.jobapplication.domain.JobApplication;
import com.example.jobsearch.jobapplication.domain.JobApplicationStatus;
import com.example.jobsearch.resume.domain.Resume;
import com.example.jobsearch.user.domain.User;
import com.example.jobsearch.vacancy.domain.Vacancy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.RollbackException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class OptimisticLockingIntegrationTest {
    @Autowired EntityManagerFactory entityManagerFactory;

    @Test
    void concurrentUserUpdateIsRejected() {
        assertConflict(User.class, firstId("User"), first -> first.setSurname("first"),
                second -> second.setSurname("second"));
    }

    @Test
    void concurrentVacancyUpdateIsRejected() {
        assertConflict(Vacancy.class, firstId("Vacancy"), first -> first.setTitle("first"),
                second -> second.setTitle("second"));
    }

    @Test
    void concurrentResumeUpdateIsRejected() {
        assertConflict(Resume.class, firstId("Resume"), first -> first.setName("first"),
                second -> second.setName("second"));
    }

    @Test
    void staleJobApplicationStatusCannotOverwriteCommittedStatus() {
        assertConflict(JobApplication.class, firstId("JobApplication"),
                first -> { first.setStatus(JobApplicationStatus.ACCEPTED);
                    first.setUpdatedAt(first.getUpdatedAt().plusSeconds(1)); },
                second -> { second.setStatus(JobApplicationStatus.REJECTED);
                    second.setUpdatedAt(second.getUpdatedAt().plusSeconds(2)); });
    }

    private long firstId(String entityName) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.createQuery("select e.id from " + entityName + " e order by e.id", Long.class)
                    .setMaxResults(1).getSingleResult();
        } finally {
            em.close();
        }
    }

    private <T> void assertConflict(Class<T> type, long id, Consumer<T> firstChange, Consumer<T> staleChange) {
        EntityManager first = entityManagerFactory.createEntityManager();
        EntityManager stale = entityManagerFactory.createEntityManager();
        first.getTransaction().begin();
        stale.getTransaction().begin();
        T firstCopy = first.find(type, id);
        T staleCopy = stale.find(type, id);
        firstChange.accept(firstCopy);
        first.getTransaction().commit();
        staleChange.accept(staleCopy);
        assertThatThrownBy(() -> stale.getTransaction().commit())
                .isInstanceOfAny(OptimisticLockException.class, RollbackException.class);
        first.close();
        stale.close();
    }
}
