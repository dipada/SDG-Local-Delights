package com.shop.shopservice.rabbitMQ;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDetails implements Serializable {
    private String name;
    private String description;
    private String image;
    private String category;
    private String brand;
    private Double price;
    private Integer stock;
    private String sellerEmail;

}
