package com.example.jobsearch.repository;

import com.example.jobsearch.model.SocialLinks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialLinksRepository extends JpaRepository<SocialLinks, Long> {
}
