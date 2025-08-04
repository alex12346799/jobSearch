package com.example.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageRequestDto {
    @NotNull(message = "Нужно указать respondedApplicants")
    private Integer respondedApplicants;

    @NotBlank(message = "content указывать обязательно")
    private String content;

    @NotNull(message = "Нужно указать дату и время")
    @PastOrPresent(message = "Дата и время не могут быть в будущем")
    private LocalDateTime timestamp;
}
