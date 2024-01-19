package com.userservice.userservice.controller;

import com.userservice.userservice.dto.ClientRequest;
import com.userservice.userservice.dto.ClientResponse;
import com.userservice.userservice.model.Client;
import com.userservice.userservice.model.User;
import com.userservice.userservice.repository.ClientRepository;
import com.userservice.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    @Autowired
    protected UserController(UserRepository userRepository, ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }



    // Post get update delete per client e seller

    //crea un nuovo client con i dati passati nel body della classe Client
    @PostMapping("/client")
    public ResponseEntity<String> createClient(@RequestBody ClientRequest clientRequest){

        // check if account exists

        Optional<User> user = userRepository.findUserByEmail(clientRequest.getEmail());

        if (user.isPresent()){
            return ResponseEntity.badRequest().body("Email già presente nel database");
        }else{
            // create new client
            CreateClient(clientRequest);
            return ResponseEntity.ok("Cliente creato con successo");
        }
    }

private void CreateClient(ClientRequest clientRequest) {
        User newUser = new User(clientRequest.getEmail(), clientRequest.getPassword(), clientRequest.getFirstName(), clientRequest.getLastName(), clientRequest.getPhoneNumber());
        userRepository.save(newUser);
        Client newClient = new Client(newUser, clientRequest.getShippingAddress());
        clientRepository.save(newClient);
    }

}
