package com.userservice.userservice.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;

    @Column(unique = true, name = "email")
    private String email;

   //TODO private UserType userType;
}
