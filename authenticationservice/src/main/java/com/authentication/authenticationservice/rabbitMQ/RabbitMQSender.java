package com.authentication.authenticationservice.rabbitMQ;

import com.authentication.authenticationservice.model.UserDetails;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;
    @Autowired
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendAddUserRequest(UserDetails userDetails) {
        System.out.println("Send msg = " + userDetails + " to routingkey" + routingkey + " exchange " + exchange);
        rabbitTemplate.convertAndSend(exchange, routingkey, userDetails);
    }

}
