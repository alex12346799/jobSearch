package com.example.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContactInfoRequestDto {
    @NotNull(message = "Обязательно укажите typeId")
    private Integer typeId;

    @NotNull(message = "Обязательно укажите resumeId")
    private Integer resumeId;

    @NotBlank(message = "Value не должен быть пустым")
    private String value;
}
