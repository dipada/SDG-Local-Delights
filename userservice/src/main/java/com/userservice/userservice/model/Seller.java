package com.userservice.userservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "_seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    User user;
    private String vat;
}
