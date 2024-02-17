package com.userservice.userservice.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String shippingAddress;
    private String paymentMethod;
    private String shopName;
    private Boolean googleAccount;
}
