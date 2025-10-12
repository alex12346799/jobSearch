
package com.example.jobsearch.model;

import com.example.jobsearch.model.Role;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String surname;

    private Integer age;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private boolean enabled = true;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    private String avatar;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
