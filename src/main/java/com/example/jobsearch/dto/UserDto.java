package com.example.jobsearch.dto;

import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;
    private String avatar;
    private String accountType;
}
