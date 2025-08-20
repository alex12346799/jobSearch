package com.example.jobsearch.dto;

import com.example.jobsearch.model.Role;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank(message = "Имя не должно быть пустым")
    private String name;

    @NotBlank(message = "Фамилия не должна быть пустым")
    private String surname;

    @NotNull(message = "Возраст указывать обязательно")
    private int age;

    @Email
    private String email;

    @NotNull(message = "Пароль обязателен")
    private String password;

    @NotBlank(message = "Телефон обязателен")
    @Pattern(regexp = "^996\\s\\(\\d{3}\\)\\s\\d{2}-\\d{2}-\\d{2}$", message = "Телефон должен быть в формате 996 (XXX) XX-XX-XX")
    private String phoneNumber;

    @NotBlank(message = "Поле адресс не должен быть пустым")
    private String address;

    @NotNull(message = "Роль обязателна")
    private Long roleId;
}
