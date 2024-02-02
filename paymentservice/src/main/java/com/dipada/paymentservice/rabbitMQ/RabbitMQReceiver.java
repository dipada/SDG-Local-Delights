package com.dipada.paymentservice.rabbitMQ;

import com.dipada.paymentservice.dto.PaymentRequest;
import com.dipada.paymentservice.model.ClientRequest;
import com.dipada.paymentservice.model.Wallet;
import com.dipada.paymentservice.repository.WalletRepository;
import org.apache.catalina.User;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

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
