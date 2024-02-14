package com.userservice.userservice.controller;

import com.userservice.userservice.dto.ClientRequest;
import com.userservice.userservice.dto.ClientResponse;
import com.userservice.userservice.dto.SellerRequest;
import com.userservice.userservice.dto.SellerResponse;
import com.userservice.userservice.model.Client;
import com.userservice.userservice.model.Seller;
import com.userservice.userservice.model.User;
import com.userservice.userservice.model.UserDetails;
import com.userservice.userservice.rabbitMQ.RabbitMQSender;
import com.userservice.userservice.repository.ClientRepository;
import com.userservice.userservice.repository.SellerRepository;
import com.userservice.userservice.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final SellerRepository sellerRepository;


    private final RabbitMQSender rabbitMQSender;

    @Autowired
    protected UserController(UserRepository userRepository, ClientRepository clientRepository, SellerRepository sellerRepository, RabbitMQSender rabbitMQSender) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.sellerRepository = sellerRepository;
        this.rabbitMQSender = rabbitMQSender;
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome to the user service");
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody UserDetails userDetails) {
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
            clientRequest.setGoogleAccount(userDetails.getGoogleAccount());

            if (!clientRequest.getGoogleAccount()){
                String response = validateRequest(clientRequest);

                if (!response.equals("ok")){

                    System.out.println(response);
                    return ResponseEntity.status(400).body(response);
                }
            }


            // check if account exists
            Optional<User> user = userRepository.findUserByEmail(clientRequest.getEmail());

            if (user.isPresent()){
                //check if is a google account
                System.out.println("User google account "+ user.get().getGoogleAccount() + "request google account "+ clientRequest.getGoogleAccount());
                if (user.get().getGoogleAccount() && !clientRequest.getGoogleAccount()){
                    System.out.println("Account google già presente nel database, associare l'account al tuo account google per accedere");
                    //aggiorna l'account con i nuovi dati
                    updateUser(user.get(), clientRequest);
                    return ResponseEntity.status(200).body("Account aggiornato con successo");
                }
                //TOOD check if is a client or a seller
                // if is a seller make new client else return bad request
                Optional<Client> client = clientRepository.findClientByUser(user.get());
                if (client.isPresent()) {
                    System.out.println("Client email già presente nel database");
                    return ResponseEntity.badRequest().body("Client email già presente nel database");
                }else {
                    makeClient(clientRequest, user.get());
                    System.out.println("Client creato con successo");
                    return ResponseEntity.status(200).body("Client creato con successo");
                }
            }else{
                // create new client
                makeClient(clientRequest, makeNewUser(clientRequest));
                rabbitMQSender.sendAddUserWallet(clientRequest);  //invio messaggio per creare wallet dello user
                System.out.println("Client creato con successo");
                return ResponseEntity.status(200).body("Client creato con successo");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @Operation(summary = "Create a new client", description = "Create a new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/client")
    public ResponseEntity<String> createClient(@RequestBody ClientRequest clientRequest){

        String response = validateRequest(clientRequest);
        if (!response.equals("ok")){
            return ResponseEntity.status(400).body(response);
        }

        // check if account exists
        Optional<User> user = userRepository.findUserByEmail(clientRequest.getEmail());

        if (user.isPresent()){
            //TOOD check if is a client or a seller
            // if is a seller make new client else return bad request
            Optional<Client> client = clientRepository.findClientByUser(user.get());
            if (client.isPresent()) {
                return ResponseEntity.badRequest().body("Client email già presente nel database");
            }else {
                makeClient(clientRequest, user.get());
                rabbitMQSender.sendAddUserWallet(clientRequest);
                return ResponseEntity.status(200).body("Client creato con successo");
            }
        }else{
            // create new client
            makeClient(clientRequest, makeNewUser(clientRequest));
            rabbitMQSender.sendAddUserWallet(clientRequest);
            return ResponseEntity.status(200).body("Client creato con successo");
        }
    }

    @Operation(summary = "Create a new seller", description = "Create a new seller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/seller")
    public ResponseEntity<String> createSeller(@RequestBody SellerRequest sellerRequest){

        String response = validateRequest(sellerRequest);
        if (!response.equals("ok")){
            return ResponseEntity.status(400).body(response);
        }

        // check if account exists
        Optional<User> user = userRepository.findUserByEmail(sellerRequest.getEmail());

        if (user.isPresent()){
            //TOOD check if is a client or a seller
            // if is a seller make new client else return bad request
            Optional<Seller> seller = sellerRepository.findSellerByUser(user.get());
            if (seller.isPresent()) {
                return ResponseEntity.badRequest().body("Seller email già presente nel database");
            }else {
                makeSeller(sellerRequest, user.get());
                return ResponseEntity.status(200).body("Seller creato con successo");
            }
        }else{
            // create new seller
            makeSeller(sellerRequest, makeNewUser(sellerRequest));
            return ResponseEntity.status(200).body("Seller creato con successo");
        }
    }

    @Operation(summary = "Get client by email", description = "Get client by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the client"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/client/{emailE}")
    public ResponseEntity<ClientResponse> getClient(@PathVariable String emailE){
        final String email = URLDecoder.decode(emailE, StandardCharsets.UTF_8);

        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()){
            Optional<Client> client = clientRepository.findClientByUser(user.get());
            if (client.isPresent()){
                ClientResponse clientResponse = new ClientResponse();
                clientResponse.setEmail(client.get().getUser().getEmail());
                clientResponse.setFirstName(client.get().getUser().getFirstName());
                clientResponse.setLastName(client.get().getUser().getLastName());
                //clientResponse.setPassword(client.get().getUser().getPassword());
                clientResponse.setPhoneNumber(client.get().getUser().getPhoneNumber());
                clientResponse.setShippingAddress(client.get().getShippingAddress());
                clientResponse.setPicture(client.get().getUser().getPicture());
                clientResponse.setGoogleAccount(client.get().getUser().getGoogleAccount());

                return ResponseEntity.status(200).body(clientResponse);
            }else{
                return ResponseEntity.status(404).body(null);
            }
        }else{
            return ResponseEntity.status(404).body(null);
        }
    }


    @Operation(summary = "Get seller by email", description = "Get seller by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the seller"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/seller/{email}")
    public ResponseEntity<SellerResponse> getSeller(@PathVariable String email){
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()){
            Optional<Seller> seller = sellerRepository.findSellerByUser(user.get());
            if (seller.isPresent()){
                SellerResponse sellerResponse = new SellerResponse();
                sellerResponse.setEmail(seller.get().getUser().getEmail());
                sellerResponse.setFirstName(seller.get().getUser().getFirstName());
                sellerResponse.setLastName(seller.get().getUser().getLastName());
                sellerResponse.setPhoneNumber(seller.get().getUser().getPhoneNumber());
                sellerResponse.setVatNumber(seller.get().getVat());
                sellerResponse.setPicture(seller.get().getUser().getPicture());
                sellerResponse.setGoogleAccount(seller.get().getUser().getGoogleAccount());

                return ResponseEntity.status(200).body(sellerResponse);
            }else{
                return ResponseEntity.status(404).body(null);
            }
        }else{
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("user/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email){
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()){
            return ResponseEntity.status(200).body(user.get());
        }else{
            return ResponseEntity.status(404).body(null);
        }
    }


    //endpoint to verify if the user is registered with email and password
    @Operation(summary = "Verify user", description = "Verify user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User verified"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String email, @RequestParam String password){

        final String decodedEmail = URLDecoder.decode(email, StandardCharsets.UTF_8);
        final String decodedPassword = URLDecoder.decode(password, StandardCharsets.UTF_8);

        Optional<User> user = userRepository.findUserByEmail(decodedEmail);
        if (user.isPresent()){
            if (user.get().getPassword().equals(decodedPassword)){
                return ResponseEntity.status(HttpStatus.OK).body("User verified");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User password not valid");
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
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

    private String validateRequest(SellerRequest sellerRequest){

        // user params
        String response = validateUserParams(sellerRequest);
        if (!response.equals("ok")) {
            return response;
        }

        // specific params of seller
        if (sellerRequest.getVatNumber() == null || sellerRequest.getVatNumber().isEmpty()) {
            return "Partita IVA non valida";
        }

        return "ok";
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

    private String validateUserParams(SellerRequest sellerRequest) {
        if (emailValidation(sellerRequest.getEmail()) && sellerRequest.getEmail() != null && !sellerRequest.getEmail().isEmpty()) {
            return "Email non valida";
        }

        if (sellerRequest.getPassword() == null || sellerRequest.getPassword().isEmpty()) {
            return "Password non valida";
        }

        if (sellerRequest.getFirstName() == null || sellerRequest.getFirstName().isEmpty()) {
            return "Nome non valido";
        }

        if (sellerRequest.getLastName() == null || sellerRequest.getLastName().isEmpty()) {
            return "Cognome non valido";
        }

        if (sellerRequest.getPhoneNumber() == null || sellerRequest.getPhoneNumber().isEmpty()) {
            return "Numero di telefono non valido";
        }

        return "ok";
    }

    private void makeClient(ClientRequest clientRequest, User newUser) {
        //User newUser = makeNewUser(clientRequest);
        Client newClient = new Client(newUser, clientRequest.getShippingAddress());
        clientRepository.save(newClient);
    }

    private void makeSeller(SellerRequest sellerRequest, User newUser) {
        //User newUser = makeNewUser(sellerRequest);
        Seller newSeller = new Seller(newUser, sellerRequest.getVatNumber());
        sellerRepository.save(newSeller);
    }

    private User makeNewUser(ClientRequest clientRequest) {
        User newUser = new User(clientRequest.getEmail(), clientRequest.getPassword(), clientRequest.getFirstName(), clientRequest.getLastName(), clientRequest.getPhoneNumber(), clientRequest.getPicture(), clientRequest.getGoogleAccount());
        userRepository.save(newUser);
        return newUser;
    }

    private User makeNewUser(SellerRequest sellerRequest) {
        User newUser = new User(sellerRequest.getEmail(), sellerRequest.getPassword(), sellerRequest.getFirstName(), sellerRequest.getLastName(), sellerRequest.getPhoneNumber(), sellerRequest.getPicture(), sellerRequest.getGoogleAccount());
        userRepository.save(newUser);
        return newUser;
    }

    private void updateUser(User user, ClientRequest clientRequest) {
        user.setPassword(clientRequest.getPassword());
        user.setPhoneNumber(clientRequest.getPhoneNumber());
        userRepository.save(user);
    }


}
