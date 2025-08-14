package com.example.jobsearch.dao.impl;

import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.SocialLinks;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@RequiredArgsConstructor
public class SocialLinksDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<SocialLinks> rowMapper = (rs, rowNum) -> {
        SocialLinks links = new SocialLinks();
        Resume resume = new Resume();
        links.setId(rs.getLong("id"));
        resume.setId(rs.getLong("resume_id"));
        links.setResume(resume);
        links.setTelegram(rs.getString("telegram"));
        links.setFacebook(rs.getString("facebook"));
        links.setLinkedin(rs.getString("linkedin"));
        return links;
    };

    public void save(SocialLinks socialLinks) {
        String sql = "INSERT INTO social_links (resume_id, telegram, facebook, linkedin) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                socialLinks.getResume().getId(),
                socialLinks.getTelegram(),
                socialLinks.getFacebook(),
                socialLinks.getLinkedin()

        );
    }
    public void update(SocialLinks socialLinks) {
        String sql = "UPDATE social_links SET telegram = ?, facebook = ?, linkedin = ? WHERE resume_id = ?";
        jdbcTemplate.update(sql,
                socialLinks.getTelegram(),
                socialLinks.getFacebook(),
                socialLinks.getLinkedin(),
                socialLinks.getResume().getId());
    }
    public void deleteByResumeId(long resumeId) {
        String sql = "DELETE FROM social_links WHERE resume_id = ?";
        jdbcTemplate.update(sql, resumeId);
    }

}

