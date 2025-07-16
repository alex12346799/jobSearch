package com.example.jobsearch.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class User {
    private long id;
    private String name;
    private String surname;
    private String role;
    private int age;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String avatar;
    private String accountType;
}
