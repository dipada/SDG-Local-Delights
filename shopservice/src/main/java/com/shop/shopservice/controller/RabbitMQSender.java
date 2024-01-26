package com.shop.shopservice.controller;

import com.shop.shopservice.rabbitMQ.ProductDetails;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    public void send(String message) {
        rabbitTemplate.convertAndSend(this.queue.getName(), message);
    }

    public void sendAddProductRequest(ProductDetails productDetails) {
        rabbitTemplate.convertAndSend(this.queue.getName(), productDetails);
    }

}
