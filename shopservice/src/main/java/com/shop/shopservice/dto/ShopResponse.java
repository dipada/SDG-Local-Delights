package com.shop.shopservice.dto;

import lombok.Data;

@Data
public class ShopResponse {

    private Long id;
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private String email;
    private String sellerEmail;
    private String longitude;
    private String latitude;
    private String imageUrl;
}
