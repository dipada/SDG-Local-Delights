package com.shop.shopservice.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "_shops", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"longitude", "latitude"})
})
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
    private String shopEmail;
    //longitudine e latitudine devono essere univoche in coppia
    @Column(name="longitude")
    private String longitude;
    @Column(name="latitude")
    private String latitude;
    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    // Relazione con Seller
    @Column(nullable = false)
    private String sellerEmail;
}
