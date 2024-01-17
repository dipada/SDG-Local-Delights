package com.userservice.userservice.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "_client")
public class Client {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)

    private String address;

}
