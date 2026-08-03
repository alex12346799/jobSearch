package com.example.jobsearch.storage.infrastructure;

import com.example.jobsearch.storage.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoredFileRepository extends JpaRepository<StoredFile, Long> {
    Optional<StoredFile> findByOwnerIdAndPurpose(Long ownerId, StoredFilePurpose purpose);
    boolean existsByOwnerIdAndPurpose(Long ownerId, StoredFilePurpose purpose);
}
