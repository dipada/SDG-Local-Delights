package com.userservice.userservice.controller;

import com.userservice.userservice.model.Client;
import com.userservice.userservice.model.User;
import com.userservice.userservice.repository.ClientRepository;
import com.userservice.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/test")
public class ClientService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/createClient")
    //String userEmail, String shippingAddress
    public void createClient() {
        String userEmail = "dan@dan";
        String shippingAddress = "via roma";
        Optional<User> user = userRepository.findUserByEmail(userEmail);
        if (user.isEmpty()) {
            createUser(userEmail);
            user = userRepository.findUserByEmail(userEmail);
        }

        if (user.isPresent()){
        Client client = new Client();
        client.setUser(user.get());
        client.setShippingAddress(shippingAddress);
        clientRepository.save(client);}
    }

    public void createUser(String userEmail) {
        User user = new User();
        user.setEmail(userEmail);
        //user.setPassword("password");
        //user.setRole("client");
        userRepository.save(user);
    }
}
