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

public class RabbitMQReceiver {

	@Autowired
	WalletRepository walletRepository;

	@RabbitListener(queues = "userQueueWallet")
	public void receiveUserDetails(ClientRequest clientRequest) {
		try {
			walletRepository.save(new Wallet(clientRequest.getEmail()));
		} catch (Exception e) {
			System.err.println(e);
		}


	}

}
