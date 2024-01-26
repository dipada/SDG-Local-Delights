package com.shop.shopservice.rabbitMQ;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = ProductDetails.class)
public class ProductDetails implements Serializable {
    private String name;
    private String description;
    private String image;
    private String category;
    private String brand;
    private Double price;
    private Integer stock;
    private String sellerEmail;
    private Long shopId;

}
