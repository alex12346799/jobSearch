
package com.example.jobsearch.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterRequest {

    @NotBlank(message = "{user.name.notblank}")
    private String name;

    @NotBlank(message = "{user.surname.notblank}")
    private String surname;

    @Email(message = "{user.email.invalid}")
    @NotBlank(message = "{user.email.notblank}")
    private String email;

    @NotBlank(message = "{user.password.notblank}")
    private String password;


    private String companyName;
}

