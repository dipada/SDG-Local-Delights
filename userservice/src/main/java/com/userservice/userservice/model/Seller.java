package com.userservice.userservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
public class Seller {

    @OneToOne
    @JoinColumn(name = "id")
    @Id
    private User user;

    private String shopName;

    public Seller(User user, String shopName) {
        this.user = user;
        this.shopName = shopName;
    }
}
