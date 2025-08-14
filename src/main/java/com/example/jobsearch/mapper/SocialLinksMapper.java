package com.example.jobsearch.mapper;

import com.example.jobsearch.dto.SocialLinkRequestDto;
import com.example.jobsearch.dto.SocialLinksResponceDto;
import com.example.jobsearch.model.SocialLinks;

public class SocialLinksMapper {
    public static SocialLinks fromDto(SocialLinkRequestDto dto) {
        if (dto == null) return null;
        SocialLinks socialLinks = new SocialLinks();
        socialLinks.setTelegram(dto.getTelegram());
        socialLinks.setFacebook(dto.getFacebook());
        socialLinks.setLinkedin(dto.getLinkedin());
        return socialLinks;
    }


    public static SocialLinksResponceDto toDto(SocialLinks socialLinks) {
        if (socialLinks == null) return null;
        SocialLinksResponceDto dto = new SocialLinksResponceDto();
        dto.setId(socialLinks.getId());
        dto.setTelegram(socialLinks.getTelegram());
        dto.setFacebook(socialLinks.getFacebook());
        dto.setLinkedin(socialLinks.getLinkedin());
        return dto;
    }
}
