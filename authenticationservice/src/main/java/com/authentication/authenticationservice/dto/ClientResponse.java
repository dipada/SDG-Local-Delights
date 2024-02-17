package com.authentication.authenticationservice.dto;

import lombok.Data;

@Data
public class ClientResponse {

        private String email;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String password;
        private String shippingAddress;
        private String picture;
        private Boolean googleAccount;
}
