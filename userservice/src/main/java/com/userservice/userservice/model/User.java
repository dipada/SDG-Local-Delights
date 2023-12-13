package com.userservice.userservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@Table(name = "_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private String password;

    //private Set<UserRole> userRoles;

    //private String phoneNumber;

    // Client fields
    //private String shippingAddress;
    //private String paymentMethod;

    // Seller fields
    //private String shopName; // TODO: check if this is needed



}
