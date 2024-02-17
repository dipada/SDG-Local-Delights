package com.payment.paymentservice.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private String email; // email of the wallet to send money to
    private long amount; // in euro
    private String orderId;
}
