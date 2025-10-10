package com.example.jobsearch.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProfileUpdateDto {
    private String name;
    private String surname;

    @Min(value = 18, message = "Возраст должен быть не меньше 18")
    @Max(value = 100, message = "Возраст не должен превышать 100")
    private Integer age;
    private String address;
    private String avatar;

    @Pattern(regexp = "^996\\s\\(\\d{3}\\)\\s\\d{2}-\\d{2}-\\d{2}$", message = "Телефон должен быть в формате 996 (XXX) XX-XX-XX")
    private String phoneNumber;
}
