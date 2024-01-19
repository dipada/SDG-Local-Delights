package com.userservice.userservice.controller;

import com.userservice.userservice.dto.ClientRequest;
import com.userservice.userservice.dto.SellerRequest;
import com.userservice.userservice.model.Client;
import com.userservice.userservice.model.Seller;
import com.userservice.userservice.model.User;
import com.userservice.userservice.repository.ClientRepository;
import com.userservice.userservice.repository.SellerRepository;
import com.userservice.userservice.repository.UserRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final SellerRepository sellerRepository;

    @Autowired
    protected UserController(UserRepository userRepository, ClientRepository clientRepository, SellerRepository sellerRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.sellerRepository = sellerRepository;
    }

    @PostMapping("/client")
    public ResponseEntity<String> createClient(@RequestBody ClientRequest clientRequest){

        String response = validateRequest(clientRequest);
        if (!response.equals("ok")){
            return ResponseEntity.badRequest().body(response);
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
                return ResponseEntity.ok("Cliente creato con successo");
            }
        }else{
            // create new client
            makeClient(clientRequest, makeNewUser(clientRequest));
            return ResponseEntity.ok("Cliente creato con successo");
        }
    }

    @PostMapping("/seller")
    public ResponseEntity<String> createSeller(@RequestBody SellerRequest sellerRequest){

        String response = validateRequest(sellerRequest);
        if (!response.equals("ok")){
            return ResponseEntity.badRequest().body(response);
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
                return ResponseEntity.ok("Seller creato con successo");
            }
        }else{
            // create new seller
            makeSeller(sellerRequest, makeNewUser(sellerRequest));
            return ResponseEntity.ok("Seller creato con successo");
        }
    }

    private boolean emailValidation(String emailAddress) {
        String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        return Pattern.compile(regexPattern)
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
        if (!emailValidation(clientRequest.getEmail()) && clientRequest.getEmail() != null && !clientRequest.getEmail().isEmpty()) {
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
        if (!emailValidation(sellerRequest.getEmail()) && sellerRequest.getEmail() != null && !sellerRequest.getEmail().isEmpty()) {
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
        User newUser = new User(clientRequest.getEmail(), clientRequest.getPassword(), clientRequest.getFirstName(), clientRequest.getLastName(), clientRequest.getPhoneNumber());
        userRepository.save(newUser);
        return newUser;
    }

    private User makeNewUser(SellerRequest sellerRequest) {
        User newUser = new User(sellerRequest.getEmail(), sellerRequest.getPassword(), sellerRequest.getFirstName(), sellerRequest.getLastName(), sellerRequest.getPhoneNumber());
        userRepository.save(newUser);
        return newUser;
    }
}
