package com.example.jobsearch.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequestRegisterDto {
//    @NotBlank(message = "Поле имя не должно быть пустым")
//    private String name;
//
//    @NotBlank(message = "Поле фамилия не должно быть пустым")
//    private String surname;
//
//
//    @Email
//    @NotBlank(message = "Нужно заполнить поле")
//    private String email;
//
//    @NotBlank(message = "Пароль обязателен")
//    private String password;
//
//
//
//    @NotNull(message = "Роль обязателна")
//    private Long roleId;

    @NotBlank(message = "{user.name.notblank}")
    private String name;

    @NotBlank(message = "{user.surname.notblank}")
    private String surname;

    @Email(message = "{user.email.invalid}")
    @NotBlank(message = "{user.email.notblank}")
    private String email;

    @NotBlank(message = "{user.password.notblank}")
    private String password;

    @NotNull(message = "{user.role.notnull}")
    private Long roleId;

}
