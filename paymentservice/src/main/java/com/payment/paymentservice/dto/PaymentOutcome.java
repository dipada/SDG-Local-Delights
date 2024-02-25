package com.payment.paymentservice.dto;

import lombok.Data;

@Data
public class PaymentOutcome {
	private String userEmail;
	private String orderId;
	private boolean paid;
}
