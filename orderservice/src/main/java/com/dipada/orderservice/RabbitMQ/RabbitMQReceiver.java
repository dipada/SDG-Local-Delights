package com.dipada.orderservice.RabbitMQ;

import com.dipada.orderservice.model.OrderStatus;
import com.dipada.orderservice.repository.OrderRepository;
import com.dipada.orderservice.dto.PaymentOutcome;
import com.dipada.orderservice.model.Order;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@EnableRabbit
public class RabbitMQReceiver {
    @Autowired
    OrderRepository orderRepository;


    @RabbitListener(queues = "orderPaymentQueue")
    public void receivePaymentOutcome(PaymentOutcome paymentOutcome) {
        System.out.println("Received <" + paymentOutcome + "> ");
        //se il pagamento è andato a buon fine, modifica l'ordine impostando paid a true
        if (paymentOutcome.isPaid()) {
            System.out.println("Order " + paymentOutcome.getOrderId() + " paid");
            Optional<Order> order = orderRepository.findById(paymentOutcome.getOrderId());
            if (order.isPresent()) {
                Order orderToModify = order.get();
                orderToModify.setPaid(true);
                orderRepository.save(orderToModify);
            }
        } else {
            System.out.println("Order " + paymentOutcome.getOrderId() + " not paid");
        }
    }
    @RabbitListener(queues = "orderTakenQueue")
    public void receiveOrderTaken(Long orderId) {
        System.out.println("Received order taken for ID: " + orderId);
        Optional<Order> order = orderRepository.findById(orderId);
        order.ifPresent(o -> {
            o.setOrderStatus(OrderStatus.IN_TRANSIT);
            orderRepository.save(o);
        });
    }

    @RabbitListener(queues = "orderDeliveredQueue")
    public void receiveOrderDelivered(Long orderId) {
        System.out.println("Received order delivered for ID: " + orderId);
        Optional<Order> order = orderRepository.findById(orderId);
        order.ifPresent(o -> {
            o.setOrderStatus(OrderStatus.COMPLETED);
            orderRepository.save(o);
        });
    }

}






