package com.userservice.userservice.rabbitMQ;

import com.userservice.userservice.controller.UserController;
import com.userservice.userservice.dto.ClientRequest;
import com.userservice.userservice.model.Client;
import com.userservice.userservice.model.User;
import com.userservice.userservice.model.UserDetails;
import com.userservice.userservice.repository.ClientRepository;
import com.userservice.userservice.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
@EnableRabbit
public class RabbitMQReceiver {

    UserRepository userRepository;
    ClientRepository clientRepository;

    public RabbitMQReceiver(UserRepository userRepository, ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }


    @Operation(summary = "Receive user details from auth service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })

    @RabbitListener(queues = "${spring.rabbitmq.queue}", ackMode = "MANUAL") //Questo ti dà la flessibilità di decidere se un messaggio è stato elaborato correttamente o meno.
                                                                // Puoi decidere di riconsegnare il messaggio alla coda (requeue) se si verifica un errore temporaneo, o di scartarlo (o inviarlo a una dead-letter queue) se si tratta di un errore permanente.
    public void receiveUserDetails(@Payload UserDetails userDetails) {


        System.out.println("Received <" + userDetails + ">");

        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setEmail(userDetails.getEmail());
            clientRequest.setPassword(userDetails.getPassword());
            clientRequest.setFirstName(userDetails.getName());
            clientRequest.setLastName(userDetails.getSurname());
            clientRequest.setPhoneNumber(userDetails.getPhoneNumber());
            clientRequest.setShippingAddress(userDetails.getShippingAddress());
            clientRequest.setPicture(userDetails.getPicture());

            String response = validateRequest(clientRequest);

            if (!response.equals("ok")){

                System.out.println(response);
                return;
            }

            // check if account exists
            Optional<User> user = userRepository.findUserByEmail(clientRequest.getEmail());

            if (user.isPresent()){
                //TOOD check if is a client or a seller
                // if is a seller make new client else return bad request
                Optional<Client> client = clientRepository.findClientByUser(user.get());
                if (client.isPresent()) {
                    System.out.println("Client email già presente nel database");
                    return;
                }else {
                    makeClient(clientRequest, user.get());
                    System.out.println("Client creato con successo");
                    return;
                }
            }else{
                // create new client
                makeClient(clientRequest, makeNewUser(clientRequest));
                System.out.println("Client creato con successo");
                return;
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ;
        }
    }



    /*
    @RabbitListener(queues = "${queue.name}")
    public void addProductFromShop(ProductDetails productDetails, Long shopId) {
        Product product = new Product();
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setCategory(productDetails.getCategory());
        product.setBrand(productDetails.getBrand());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());
        product.setImage(productDetails.getImage());

        productRepository.save(product);
        System.out.println("Received < add product >");
    }

     */
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
        //User newUser = makeNewUser(clientRequest);
        Client newClient = new Client(newUser, clientRequest.getShippingAddress());
        clientRepository.save(newClient);
    }

    private User makeNewUser(ClientRequest clientRequest) {
        User newUser = new User(clientRequest.getEmail(), clientRequest.getPassword(), clientRequest.getFirstName(), clientRequest.getLastName(), clientRequest.getPhoneNumber(), clientRequest.getPicture());
        userRepository.save(newUser);
        return newUser;
    }

}
