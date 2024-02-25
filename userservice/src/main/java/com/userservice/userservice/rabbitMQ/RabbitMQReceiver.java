package com.userservice.userservice.rabbitMQ;

import com.userservice.userservice.dto.ClientRequest;
import com.userservice.userservice.model.Client;
import com.userservice.userservice.model.User;
import com.userservice.userservice.model.UserDetails;
import com.userservice.userservice.repository.ClientRepository;
import com.userservice.userservice.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
@EnableRabbit

public class RabbitMQReceiver {
	@Autowired
	private final RabbitMQSender rabbitMQSender;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ClientRepository clientRepository;

	public RabbitMQReceiver(RabbitMQSender rabbitMQSender) {
		this.rabbitMQSender = rabbitMQSender;
	}

	@RabbitListener(queues = "userQueue")
	public void receiveUserDetails(@Payload UserDetails userDetails) {
		try {
			ClientRequest clientRequest = new ClientRequest();
			clientRequest.setEmail(userDetails.getEmail());
			clientRequest.setPassword(userDetails.getPassword());
			clientRequest.setFirstName(userDetails.getFirstName());
			clientRequest.setLastName(userDetails.getLastName());
			clientRequest.setPhoneNumber(userDetails.getPhoneNumber());
			clientRequest.setShippingAddress(userDetails.getShippingAddress());
			clientRequest.setPicture(userDetails.getPicture());
			clientRequest.setGoogleAccount(userDetails.getGoogleAccount());

			if (!clientRequest.getGoogleAccount()) {
				String response = validateRequest(clientRequest);

				if (!response.equals("ok")) {
					return;
				}
			}


			// check if account exists
			Optional<User> user = userRepository.findUserByEmail(clientRequest.getEmail());

			if (user.isPresent()) {
				//check if is a google account
				if (user.get().getGoogleAccount() && !clientRequest.getGoogleAccount()) {
					//aggiorna l'account con i nuovi dati
					updateUser(user.get(), clientRequest);
					return;
				}
				//TOOD check if is a client or a seller
				// if is a seller make new client else return bad request
				Optional<Client> client = clientRepository.findClientByUser(user.get());
				if (client.isPresent()) {
					return;
				} else {
					makeClient(clientRequest, user.get());
					return;
				}
			} else {
				// create new client
				makeClient(clientRequest, makeNewUser(clientRequest));
				rabbitMQSender.sendAddUserWallet(clientRequest);
				return;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}
	}

	private String validateRequest(ClientRequest clientRequest) {

		// user params
		String response = validateUserParams(clientRequest);
		if (!response.equals("ok")) {
			return response;
		}

		// specific params of client
		if (clientRequest.getShippingAddress() == null || clientRequest.getShippingAddress().isEmpty()) {
			return "Indirizzo di spedizione non valido";
		}

		return "ok";
	}

	private String validateUserParams(ClientRequest clientRequest) {
		if (emailValidation(clientRequest.getEmail()) && clientRequest.getEmail() != null && !clientRequest.getEmail().isEmpty()) {
			return "Email non valida";
		}

		if (clientRequest.getPassword() == null || clientRequest.getPassword().isEmpty()) {
			return "Password non valida";
		}

		if (clientRequest.getFirstName() == null || clientRequest.getFirstName().isEmpty()) {
			return "Nome non valido";
		}

		if (clientRequest.getLastName() == null || clientRequest.getLastName().isEmpty()) {
			return "Cognome non valido";
		}

		if (clientRequest.getPhoneNumber() == null || clientRequest.getPhoneNumber().isEmpty()) {
			return "Numero di telefono non valido";
		}

		return "ok";
	}

	private boolean emailValidation(String emailAddress) {
		String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\." +
						"[a-zA-Z0-9_+&*-]+)*@" +
						"(?:[a-zA-Z0-9-]+\\.)+[a-z" +
						"A-Z]{2,7}$";
		return !Pattern.compile(regexPattern)
						.matcher(emailAddress)
						.matches();
	}

	private void makeClient(ClientRequest clientRequest, User newUser) {
		Client newClient = new Client(newUser, clientRequest.getShippingAddress());
		clientRepository.save(newClient);
	}

	private User makeNewUser(ClientRequest clientRequest) {
		User newUser = new User(clientRequest.getEmail(), clientRequest.getPassword(), clientRequest.getFirstName(), clientRequest.getLastName(), clientRequest.getPhoneNumber(), clientRequest.getPicture(), clientRequest.getGoogleAccount());
		userRepository.save(newUser);
		return newUser;
	}

	private void updateUser(User user, ClientRequest clientRequest) {
		user.setPassword(clientRequest.getPassword());
		user.setPhoneNumber(clientRequest.getPhoneNumber());
		userRepository.save(user);
	}

}
