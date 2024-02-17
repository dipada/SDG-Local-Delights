package com.payment.paymentservice.rabbitMQ;

import com.payment.paymentservice.model.ClientRequest;
import com.payment.paymentservice.model.Wallet;
import com.payment.paymentservice.repository.WalletRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit

public class RabbitMQReceiver{

    @Autowired
    WalletRepository walletRepository;

    @RabbitListener(queues = "userQueueWallet")
    public void receiveUserDetails(ClientRequest clientRequest) {
        System.out.println("Received <" + clientRequest + ">");
        try {
            walletRepository.save(new Wallet(clientRequest.getEmail()));
            System.out.println("Wallet created");
        } catch (Exception e) {
            System.out.println(e);
        }


    }

}
