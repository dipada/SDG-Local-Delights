package com.dipada.orderservice.dto;

import lombok.Data;

@Data
public class PaymentOutcome {
    private String userEmail;
    private Long orderId;
    private boolean paid;
}

