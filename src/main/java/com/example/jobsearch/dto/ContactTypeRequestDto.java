package com.example.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactTypeRequestDto {
    @NotBlank(message = "Нужно обязательно указать type")
    private String type;
}
