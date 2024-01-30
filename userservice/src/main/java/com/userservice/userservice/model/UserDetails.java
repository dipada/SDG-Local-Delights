package com.userservice.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String picture;
    private String phoneNumber;
    private String shippingAddress;

}
