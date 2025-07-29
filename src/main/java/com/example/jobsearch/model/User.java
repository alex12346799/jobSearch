package com.example.jobsearch.model;


import lombok.Data;


@Data
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
    private String enabled;
    private String username;
    private int roleId;
}
