package com.userservice.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = "id")
    private User user;
    private String shippingAddress;
    private String paymentMethod;


    public Customer(User user, String shippingAddress, String paymentMethod) {
        this.user = user;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }
}
