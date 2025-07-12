package com.example.jobsearch.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private int id;
    private String name;
    private String surname;
    private int age;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String avatar;
    private String accountType;
}
