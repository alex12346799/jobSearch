package com.example.jobsearch.mapper;

import com.example.jobsearch.dto.ContactTypeRequestDto;
import com.example.jobsearch.dto.ContactTypeResponseDto;
import com.example.jobsearch.model.ContactType;

public class ContactTypeMapper {
    public static ContactTypeResponseDto toDto(ContactType contactType) {
        if(contactType == null) return null;
        ContactTypeResponseDto dto = new ContactTypeResponseDto();
        dto.setId(contactType.getId());
        dto.setType(contactType.getType());
        return dto;
    }
    public static ContactType fromDto(ContactTypeRequestDto contactTypeRequestDto) {
        if(contactTypeRequestDto == null) return null;
        ContactType contactType = new ContactType();
        contactType.setType(contactTypeRequestDto.getType());
        return contactType;
    }
}
