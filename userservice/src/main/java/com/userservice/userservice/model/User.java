package com.userservice.userservice.model;

import jakarta.persistence.OneToOne;

public class User {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    private String phoneNumber;

    private Client clientDetails;
    private Seller sellerDetails;
}
