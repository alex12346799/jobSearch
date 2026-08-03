package com.example.jobsearch.auth.persistence;

import com.example.jobsearch.auth.domain.PasswordResetToken;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<PasswordResetToken> findByTokenHash(String tokenHash);

    @Modifying
    @Query("""
            update PasswordResetToken token
            set token.revokedAt = :now
            where token.user.id = :userId
              and token.usedAt is null and token.revokedAt is null and token.expiresAt > :now
            """)
    int revokeAllActiveByUserId(@Param("userId") Long userId, @Param("now") Instant now);
}
