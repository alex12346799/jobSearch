package com.example.jobsearch.migration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class EmployerRoleMigrationTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void currentRolesContainOnlyApplicantEmployerAndAdmin() {
        Set<String> roles = Set.copyOf(jdbcTemplate.queryForList(
                "SELECT role_name FROM roles",
                String.class
        ));

        assertThat(roles).containsExactlyInAnyOrder("APPLICANT", "EMPLOYER", "ADMIN");
        assertThat(roles).doesNotContain("EMPLOYEE");
    }

    @Test
    void userWithHistoricalEmployerRoleKeepsItsRoleLink() {
        Long employerRoleId = jdbcTemplate.queryForObject(
                "SELECT id FROM roles WHERE role_name = 'EMPLOYER'",
                Long.class
        );
        Long janeRoleId = jdbcTemplate.queryForObject(
                "SELECT role_id FROM users WHERE name = 'Jane'",
                Long.class
        );

        assertThat(janeRoleId).isEqualTo(employerRoleId);
        assertThat(employerRoleId).isEqualTo(1L);
    }

    @Test
    void roleNameHasUniqueConstraint() {
        Integer constraintCount = jdbcTemplate.queryForObject("""
                SELECT COUNT(*)
                FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
                WHERE TABLE_NAME = 'ROLES'
                  AND CONSTRAINT_NAME = 'UQ_ROLES_ROLE_NAME'
                  AND CONSTRAINT_TYPE = 'UNIQUE'
                """, Integer.class);

        assertThat(constraintCount).isEqualTo(1);
    }

    @Test
    void refreshTokenSchemaAndNormalizedEmailConstraintExist() {
        Integer refreshTable = jdbcTemplate.queryForObject("""
                SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'REFRESH_TOKENS'
                """, Integer.class);
        Integer refreshUnique = jdbcTemplate.queryForObject("""
                SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
                WHERE TABLE_NAME = 'REFRESH_TOKENS'
                  AND CONSTRAINT_NAME = 'UQ_REFRESH_TOKENS_TOKEN_HASH'
                  AND CONSTRAINT_TYPE = 'UNIQUE'
                """, Integer.class);
        Integer nonNormalizedEmails = jdbcTemplate.queryForObject("""
                SELECT COUNT(*) FROM users WHERE email <> LOWER(TRIM(email))
                """, Integer.class);

        assertThat(refreshTable).isEqualTo(1);
        assertThat(refreshUnique).isEqualTo(1);
        assertThat(nonNormalizedEmails).isZero();
    }

    @Test
    void securePasswordResetSchemaReplacesLegacyColumn() {
        Integer table = jdbcTemplate.queryForObject("""
                SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES
                WHERE TABLE_NAME = 'PASSWORD_RESET_TOKENS'
                """, Integer.class);
        Integer uniqueHash = jdbcTemplate.queryForObject("""
                SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
                WHERE TABLE_NAME = 'PASSWORD_RESET_TOKENS'
                  AND CONSTRAINT_NAME = 'UQ_PASSWORD_RESET_TOKENS_TOKEN_HASH'
                  AND CONSTRAINT_TYPE = 'UNIQUE'
                """, Integer.class);
        Integer legacyColumn = jdbcTemplate.queryForObject("""
                SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
                WHERE TABLE_NAME = 'USERS' AND COLUMN_NAME = 'RESET_PASSWORD_TOKEN'
                """, Integer.class);

        assertThat(table).isEqualTo(1);
        assertThat(uniqueHash).isEqualTo(1);
        assertThat(legacyColumn).isZero();
    }
}
