package com.example.jobsearch.mapper;

import com.example.jobsearch.dto.ContactInfoRequestDto;
import com.example.jobsearch.dto.ContactInfoResponseDto;
import com.example.jobsearch.model.ContactInfo;

public class ContactInfoMapper {
    public static ContactInfoResponseDto toDto(ContactInfo contactInfo) {
        if(contactInfo == null) return null;
        ContactInfoResponseDto dto = new ContactInfoResponseDto();
        dto.setId(contactInfo.getId());
        dto.setTypeId(contactInfo.getTypeId());
        dto.setResumeId(contactInfo.getResumeId());
        dto.setValue(contactInfo.getValue());
        return dto;
    }
    public static ContactInfo fromDto(ContactInfoRequestDto dto){
        if(dto == null) return null;
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setTypeId(dto.getTypeId());
        contactInfo.setResumeId(dto.getResumeId());
        contactInfo.setValue(dto.getValue());
        return contactInfo;
    }
}
