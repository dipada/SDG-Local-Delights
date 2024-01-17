package com.userservice.userservice.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "_client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private User user;

    private String shippingAddress;
}
