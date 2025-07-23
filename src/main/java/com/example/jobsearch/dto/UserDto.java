package com.example.jobsearch.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String surname;
    private String phoneNumber;
    private String address;
    private String avatar;
    private String accountType;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 24, message = "Length must be > 6 and <24")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-z]).+$"
    , message = "Shoud contain at least one uppercase letter, one number")
    @NotBlank
    private String role;
}
