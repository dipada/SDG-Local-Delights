package com.userservice.userservice.model;

public class Seller {

    private final User user;

    private String shopName;

    public Seller(User user, String shopName) {
        this.user = user;
        this.shopName = shopName;
    }
}
