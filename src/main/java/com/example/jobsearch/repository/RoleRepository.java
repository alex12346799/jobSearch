package com.example.jobsearch.repository;

import com.example.jobsearch.model.Role;
import com.example.jobsearch.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
