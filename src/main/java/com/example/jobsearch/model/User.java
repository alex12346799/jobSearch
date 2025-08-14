package com.example.jobsearch.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String role;
    private int age;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;
    private String address;
    private String avatar;

    @Column(name = "account_type")
    private String accountType;

}
