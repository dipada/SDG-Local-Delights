//package com.userservice.userservice.controller;

import com.userservice.userservice.model.Client;
import com.userservice.userservice.model.Client;
import com.userservice.userservice.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
/*&
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    protected UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Operation(summary = "Get user by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user"),
            @ApiResponse(responseCode = "400", description = "Invalid email supplied"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @GetMapping()
    public ResponseEntity<Optional<Client>> getUserByEmail(@RequestParam String email) {
        Optional<Client> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all users")})
    @GetMapping("/all")
    public ResponseEntity<Iterable<Client>> getAllUsers() {
        Iterable<Client> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "409", description = "Email already exists")})
    @PostMapping()
    public ResponseEntity<String> addUser(@RequestBody @NotNull Client user) {
        //TODO check if email already exists and return 409
        Optional<Client> userByEmail = userRepository.findUserByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("email already exists");
        }
        userRepository.save(user);
        return ResponseEntity.ok("user added successfully");
    }
}


 */