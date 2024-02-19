package com.delivery.deliveryservice.rabbitMQ;

import com.delivery.deliveryservice.model.Delivery;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {

  private final RabbitTemplate rabbitTemplate;

  @Qualifier("orderTakenQueue")
  private final Queue orderTakenQueue;

  @Qualifier("orderDeliveredQueue")
  private final Queue orderDeliveredQueue;

  @Autowired
  public RabbitMQSender(RabbitTemplate rabbitTemplate, Queue orderTakenQueue, Queue orderDeliveredQueue) {
    this.rabbitTemplate = rabbitTemplate;
    this.orderTakenQueue = orderTakenQueue;
    this.orderDeliveredQueue = orderDeliveredQueue;
  }

  public void sendOrderTaken(Delivery delivery) {
    System.out.println("Sending order taken message for order ID: " + delivery.getOrderId());
    rabbitTemplate.convertAndSend(this.orderTakenQueue.getName(), delivery.getOrderId());
  }

  public void sendOrderDelivered(Delivery delivery) {
    System.out.println("Sending order delivered message for order ID: " + delivery.getOrderId());
    rabbitTemplate.convertAndSend(this.orderDeliveredQueue.getName(), delivery.getOrderId());
  }
}
