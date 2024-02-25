package com.authentication.authenticationservice.rabbitMQ;

import com.authentication.authenticationservice.model.UserDetails;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQSender {
	@Autowired
	private final RabbitTemplate rabbitTemplate;

	@Qualifier("queue")
	@Autowired
	private final Queue userQueue;


	public RabbitMQSender(RabbitTemplate rabbitTemplate, @Qualifier("queue") Queue userQueue) {
		this.rabbitTemplate = rabbitTemplate;
		this.userQueue = userQueue;
	}

	public void sendAddUserRequest(UserDetails userDetails) {
		rabbitTemplate.convertAndSend(this.userQueue.getName(), userDetails);
	}


}