
package com.example.jobsearch.dto.user;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private String phoneNumber;
    private String address;
    private String avatar;
    private String companyName;
    private String roleName;
}
