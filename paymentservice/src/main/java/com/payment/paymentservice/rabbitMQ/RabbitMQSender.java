package com.payment.paymentservice.rabbitMQ;

import com.payment.paymentservice.dto.PaymentOutcome;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {
	@Autowired
	private final RabbitTemplate rabbitTemplate;

	@Qualifier("queuePayment")
	@Autowired
	private final Queue orderPaymentQueue;

	public RabbitMQSender(RabbitTemplate rabbitTemplate, @Qualifier("queuePayment") Queue orderPaymentQueue) {
		this.rabbitTemplate = rabbitTemplate;
		this.orderPaymentQueue = orderPaymentQueue;
	}


	public void sendPaymentOutcome(PaymentOutcome paymentOutcome) {
		rabbitTemplate.convertAndSend(this.orderPaymentQueue.getName(), paymentOutcome);
	}
}
