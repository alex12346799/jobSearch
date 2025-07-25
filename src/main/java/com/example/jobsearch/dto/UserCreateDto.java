package com.example.jobsearch.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateDto {
    @NotBlank(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank(message = "Фамилия не должна быть пустой")
    @Size(min = 2, max = 50)
    private String surname;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Номер телефона должен быть валидным")
    //тут получается нужно чтобы номер состоял из цифл и был в диапазоне от 0-9
    // и чтобы таких цифр было от 10-15
    private String phoneNumber;

    @Size(max = 255, message = "Адрес должен быть не длиннее 255 символов")
    private String address;


    private String avatar;

    @Size(max = 100, message = "Тип аккаунта слишком длинный")
    private String accountType;

    @NotBlank(message = "Email не может быть пустым")
    @Email
    private String email;

    @Size(min = 6, max = 24, message = "Length must be > 6 and <24")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-z]).+$"
            , message = "Shoud contain at least one uppercase letter, one number")
    private String password;

    @NotBlank(message = "Роль нужно указать обязательно")
    private String role;
}

