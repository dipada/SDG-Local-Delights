package com.dipada.paymentservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "_wallet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    private long id;

    @Column(unique = true)
    private String email;

    private long balance; // in cents euro

    public Wallet(String email) {
        this.email = email;
        this.balance = 0;
    }
}
