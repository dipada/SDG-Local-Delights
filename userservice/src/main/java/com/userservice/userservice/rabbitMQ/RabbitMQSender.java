package com.userservice.userservice.rabbitMQ;


import com.userservice.userservice.dto.ClientRequest;
import com.userservice.userservice.model.UserDetails;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;



@Component
public class RabbitMQSender{
    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @Qualifier("queueWallet")
    @Autowired
    private final Queue queueWallet;



    public RabbitMQSender(RabbitTemplate rabbitTemplate, @Qualifier("queueWallet") Queue queueWallet){
        this.rabbitTemplate = rabbitTemplate;
        this.queueWallet = queueWallet;
    }

    public void sendAddUserWallet(ClientRequest clientRequest){
        System.out.println("Send msg to add wallet = " + clientRequest + " to queue " + queueWallet.getName());
        rabbitTemplate.convertAndSend(this.queueWallet.getName(), clientRequest);
    }


}