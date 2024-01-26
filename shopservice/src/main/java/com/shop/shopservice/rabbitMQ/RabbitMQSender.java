package com.shop.shopservice.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQSender {
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;
    @Autowired
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    /*
    public void send(String message) {
        rabbitTemplate.convertAndSend(this.queue.getName(), message);
    }

     */

    public void sendAddProductRequest(ProductDetails productDetails, Long shopId) {
        productDetails.setShopId(shopId);
        rabbitTemplate.convertAndSend(exchange, routingkey, productDetails);
    }

}
