
package com.example.jobsearch.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserEditRequest {

    @NotBlank(message = "{user.name.notblank}")
    private String name;

    @NotBlank(message = "{user.surname.notblank}")
    private String surname;

    @NotNull(message = "{user.age.notnull}")
    private Integer age;

    @NotBlank(message = "{user.phone.notblank}")
    private String phoneNumber;

    @NotBlank(message = "{user.address.notblank}")
    private String address;
}
