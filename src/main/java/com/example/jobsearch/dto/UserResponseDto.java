package com.example.jobsearch.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private long id;
    private String name;
    private String surname;
    private int age;
    private String email;
    private String phoneNumber;
    private String address;
    private String avatar;
    private String accountType;
}
