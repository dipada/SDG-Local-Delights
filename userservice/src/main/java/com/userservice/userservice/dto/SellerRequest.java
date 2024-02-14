package com.userservice.userservice.dto;

import lombok.Data;
@Data
public class SellerRequest {
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String vatNumber;
        private String picture;
        private Boolean googleAccount;
}
