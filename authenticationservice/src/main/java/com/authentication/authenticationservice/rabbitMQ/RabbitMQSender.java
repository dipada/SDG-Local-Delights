package com.authentication.authenticationservice.rabbitMQ;

import com.authentication.authenticationservice.model.UserDetails;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;



@Component
public class RabbitMQSender{
    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @Qualifier("queue")
    @Autowired
    private final Queue userQueue;

    @Qualifier("queue2")
    @Autowired
    private final Queue userQueueWallet;

    public RabbitMQSender(RabbitTemplate rabbitTemplate, @Qualifier("queue") Queue userQueue, @Qualifier("queue2") Queue userQueueWallet){
        this.rabbitTemplate = rabbitTemplate;
        this.userQueue = userQueue;
        this.userQueueWallet = userQueueWallet;
    }

    public void sendAddUserRequest(UserDetails userDetails){
        System.out.println("Send msg = " + userDetails + " to queue " + userQueue.getName());
        rabbitTemplate.convertAndSend(this.userQueue.getName(), userDetails);
        rabbitTemplate.convertAndSend(this.userQueueWallet.getName(), userDetails);
    }


}