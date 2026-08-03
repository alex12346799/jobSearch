package com.example.jobsearch.storage.domain;

import com.example.jobsearch.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "stored_files",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_stored_files_object_key", columnNames = "object_key"),
                @UniqueConstraint(name = "uq_stored_files_owner_purpose", columnNames = {"owner_id", "purpose"})
        })
public class StoredFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StoredFilePurpose purpose;

    @Column(name = "object_key", nullable = false, length = 255)
    private String objectKey;

    @Column(name = "content_type", nullable = false, length = 50)
    private String contentType;

    @Column(name = "file_size", nullable = false)
    private long size;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
