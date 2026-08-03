package com.example.jobsearch.resume.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "social_links")
public class SocialLinks {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;
    private String telegram;
    private String facebook;
    private String linkedin;
}
