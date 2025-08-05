package com.example.jobsearch.mapper;

import com.example.jobsearch.dto.MessageRequestDto;
import com.example.jobsearch.dto.MessageResponseDto;
import com.example.jobsearch.model.Message;

public class MessageMapper {
    public static MessageResponseDto toDto(Message message) {
        if(message == null) return null;
        MessageResponseDto dto = new MessageResponseDto();
        dto.setRespondedApplicants(message.getRespondedApplicants());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp());
        return dto;
    }
    public static Message fromDto(MessageRequestDto dto){
        if(dto == null) return null;
        Message message = new Message();
        message.setRespondedApplicants(dto.getRespondedApplicants());
        message.setContent(dto.getContent());
        message.setTimestamp(dto.getTimestamp());
        return message;
    }
}
