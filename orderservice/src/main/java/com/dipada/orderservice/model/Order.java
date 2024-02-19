package com.dipada.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userEmail;
    private Long shopId;
    private Boolean paid;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> listOfProductsIds;

    private OrderStatus orderStatus;
    private Double amount;
    private String timestamp;
    private String shippingAddress;
    private Long deliveryId;
}
