package com.example.jobsearch.dto;

import com.example.jobsearch.validation.ApplicantGroup;
import com.example.jobsearch.validation.EmployerGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequestRegisterDto {


    @NotBlank(message = "{user.name.notblank}", groups = ApplicantGroup.class)
    private String name;

    @NotBlank(message = "{user.surname.notblank}", groups = ApplicantGroup.class)
    private String surname;

    @Email(message = "{user.email.invalid}")
    @NotBlank(message = "{user.email.notblank}")
    private String email;

    @NotBlank(message = "{user.password.notblank}")
    private String password;

//    @NotNull(message = "{user.role.notnull}")
    private Long roleId;

    @NotBlank(message = "{register.company_name}", groups = EmployerGroup.class)
    private String companyName;

}
