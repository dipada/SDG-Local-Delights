package com.shop.shopservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "_shops")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private String email;
    private String longitude;
    private String latitude;
    private String imageUrl;

    // Relazione con Seller
    @Column(nullable = false)
    private String sellerEmail;
}
