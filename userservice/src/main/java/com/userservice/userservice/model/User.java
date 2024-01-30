package com.userservice.userservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.aspectj.lang.annotation.RequiredTypes;

@Entity
@Table(name = "_user")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, name = "email")
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String picture;

    public User(String email, String password, String firstName, String lastName, String phoneNumber, String picture) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.picture = picture;
    }

}
