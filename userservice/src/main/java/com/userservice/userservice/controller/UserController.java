package com.userservice.userservice.controller;

import com.userservice.userservice.model.User;
import com.userservice.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    protected UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping()
    public ResponseEntity<Optional<User>> getUser(@RequestParam String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);

    }

    @PostMapping()
    public void addUser(@RequestBody User user) {
        userRepository.save(user);
    }
}
