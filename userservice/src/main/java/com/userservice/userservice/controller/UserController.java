package com.userservice.userservice.controller;

import com.userservice.userservice.dto.UserRequest;
import com.userservice.userservice.model.User;
import com.userservice.userservice.model.UserRole;
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
import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    protected UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Operation(summary = "Get user by email")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the user"), @ApiResponse(responseCode = "400", description = "Invalid email supplied"), @ApiResponse(responseCode = "404", description = "User not found")})
    @GetMapping()
    public ResponseEntity<Optional<User>> getUserByEmail(@RequestParam String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found all users")})
    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Create new user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User created successfully"), @ApiResponse(responseCode = "409", description = "Email already exists")})
    @PostMapping()
    public ResponseEntity<String> createCustomer(@RequestBody @NotNull UserRequest userRequest) {
        Optional<User> userByEmail = userRepository.findUserByEmail(userRequest.getEmail());
        if (userByEmail.isPresent()) {
            if (userByEmail.get().getUserRoles().contains(UserRole.CUSTOMER)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            } else {
                userByEmail.get().getUserRoles().add(UserRole.CUSTOMER);
                userRepository.save(userByEmail.get());
                return ResponseEntity.ok("User created successfully");
            }
        }

        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .userRoles(Set.of(UserRole.CUSTOMER))
                .phoneNumber(userRequest.getPhoneNumber())
                .shippingAddress(userRequest.getShippingAddress())
                .paymentMethod(userRequest.getPaymentMethod())
                .password(userRequest.getPassword())
                .build();

        userRepository.save(user);


        return ResponseEntity.ok("User created successfully");
    }

    //public ResponseEntity<UserResponse> createSeller(@RequestBody @NotNull UserRequest userRequest) {

    //}
}
