package com.userservice.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SellerResponse {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String vatNumber;

}
