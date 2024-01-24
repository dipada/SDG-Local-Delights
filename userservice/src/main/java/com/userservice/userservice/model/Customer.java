package com.userservice.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
public class Customer {
    @OneToOne
    @JoinColumn(name = "id")
    @Id
    private User user;
    private String shippingAddress;
    private String paymentMethod;


    public Customer(User user, String shippingAddress, String paymentMethod) {
        this.user = user;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }
}
