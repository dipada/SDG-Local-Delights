package com.userservice.userservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seller")
@Data
@NoArgsConstructor
public class Seller {

    @OneToOne
    private User user;

    @Id
    @JoinColumn(name = "_user_email")
    private String email;

    private String shopName;

    public Seller(User user, String shopName) {
        this.user = user;
        this.shopName = shopName;
        this.email = user.getEmail();
    }
}
