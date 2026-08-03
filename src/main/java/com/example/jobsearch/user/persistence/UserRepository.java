package com.example.jobsearch.user.persistence;

import com.example.jobsearch.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import com.example.jobsearch.user.domain.RoleName;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u.name FROM User u WHERE u.id = :id")
    String findNameById(@Param("id") Long id);

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailIgnoreCase(String email);

    @EntityGraph(attributePaths = "role")
    @Query("""
            select u from User u
            where (:search = '' or lower(u.name) like lower(concat('%', :search, '%'))
              or lower(coalesce(u.surname, '')) like lower(concat('%', :search, '%'))
              or lower(u.email) like lower(concat('%', :search, '%')))
              and (:role is null or u.role.name = :role)
              and (:enabled is null or u.enabled = :enabled)
            """)
    Page<User> searchForAdmin(@Param("search") String search, @Param("role") RoleName role,
                              @Param("enabled") Boolean enabled, Pageable pageable);

    @EntityGraph(attributePaths = "role")
    @Query("select u from User u where u.id = :id")
    Optional<User> findDetailedById(@Param("id") Long id);
}
