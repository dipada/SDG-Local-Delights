package com.dipada.orderservice.RabbitMQ;

import com.dipada.orderservice.dto.PaymentOutcome;
import com.dipada.orderservice.model.Order;
import com.dipada.orderservice.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@EnableRabbit
public class RabbitMQReceiver {
	@Autowired
	OrderRepository orderRepository;


	@RabbitListener(queues = "orderPaymentQueue")
	public void receivePaymentOutcome(PaymentOutcome paymentOutcome) {
		if (paymentOutcome.isPaid()) {
			Optional<Order> order = orderRepository.findById(paymentOutcome.getOrderId());
			if (order.isPresent()) {
				Order orderToModify = order.get();
				orderToModify.setPaid(true);
				orderRepository.save(orderToModify);
			}
		}
	}
}






