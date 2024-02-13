package com.authentication.authenticationservice.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.authentication.authenticationservice.dto.ClientResponse;
import com.authentication.authenticationservice.dto.LoginRequest;
import com.authentication.authenticationservice.model.UserDetails;
import com.authentication.authenticationservice.rabbitMQ.RabbitMQSender;
import com.nimbusds.jose.Header;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    private final RabbitMQSender rabbitMQSender;

    // RestTemplate per effettuare chiamate HTTP ad altri microservizi
    private final RestTemplate restTemplate;


    @Autowired
    public AuthController(RabbitMQSender rabbitMQSender, RestTemplate restTemplate) {
        this.rabbitMQSender = rabbitMQSender;
        this.restTemplate = restTemplate;
    }


    // Entrypoint will be triggered by Spring Security when the user is not authenticated.
    // This method is called when the user is successfully authenticated with Google.
    @Operation(summary = "Login with Google")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirect to the client with the token"),
    })
    @GetMapping("/google")
    public ResponseEntity<String> successLogin(Authentication auth, @RequestParam(name = "redirect_uri", required = false) String redirectUri) {
        OAuth2User user = (OAuth2User) auth.getPrincipal();

        //make jwt token
        String token = JWT.create().withSubject(user.getAttribute("email")).withClaim("name", (String) user.getAttribute("given_name")).withClaim("surname", (String) user.getAttribute("family_name")).withClaim("picture", (String) user.getAttribute("picture")).withExpiresAt(new Date(System.currentTimeMillis() + 600000)) //10 minuti
                .sign(Algorithm.HMAC256("secret"));

        if (redirectUri == null || redirectUri.isEmpty()) {
            redirectUri = "http://localhost:5173/redirect/oauth";
        }
        //send a message to the user service to create the user
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(user.getAttribute("email"));
        userDetails.setName(user.getAttribute("given_name"));
        userDetails.setSurname(user.getAttribute("family_name"));
        userDetails.setPicture(user.getAttribute("picture"));
        userDetails.setGoogleAccount(true);

        rabbitMQSender.sendAddUserRequest(userDetails);

        // redirect to the client with the token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", URLDecoder.decode(redirectUri, StandardCharsets.UTF_8) + "?token=" + token);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }


    // Entrypoint for signup page
    /*
    @Operation(summary = "Signup")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirect to the client"),
    })
    @PostMapping("/signup")
    public ResponseEntity<String> signup (@RequestBody UserDetails userDetails) {
        //manda un messaggio via rabbit a user service per creare l'utente
        userDetails.setGoogleAccount(false);
        rabbitMQSender.sendAddUserRequest(userDetails);

        String redirectUri = "http://localhost:5173/login";

        // redirect to the client
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUri));
        return ResponseEntity.status(HttpStatus.OK).body("User add request sent successfully");
    }

     */

    @Value("${userservice.url}")
    String userServiceUrl;

    @Operation(summary = "Signup")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirect to the client"),
    })
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDetails userDetails) {
        String redirectUri;
        //manda un messaggio via rabbit a user service per creare l'utente
        userDetails.setGoogleAccount(false);
        String verifyUrl = userServiceUrl + "/api/v1/user/createUser";
        //rest request to user service to singup

        ResponseEntity<ClientResponse> response = restTemplate.postForEntity(verifyUrl, userDetails, ClientResponse.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            // Credenziali non valide
            return ResponseEntity.status(response.getStatusCode()).body("Invalid request");
        }
        //rabbitMQSender.sendAddUserRequest(userDetails);

        System.out.println("User created successfully");
        redirectUri = "http://localhost:5173/login";
        // redirect to the client
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUri));
        return ResponseEntity.status(HttpStatus.OK).body("User add request sent successfully");
    }


    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirect to the client"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "An error occurred"),
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // URL di UserService per la verifica dell'utente
        String verifyUrl = userServiceUrl + "/api/v1/user/verify?email=" + loginRequest.getEmail() + "&password=" + loginRequest.getPassword();
        System.out.println("URL A CUI HO MANDATO LA RICHIESTA VERIFY USER: " + verifyUrl);

        // Invia richiesta a UserService per verificare l'utente
        ResponseEntity<String> response = restTemplate.getForEntity(verifyUrl, String.class);
        System.out.println("response: " + response);

        if (response.getStatusCode() != HttpStatus.OK) {
            // Credenziali non valide
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        System.out.println("CREDENZIALI VALIDE");
        String redirectUri = "http://localhost:5173/";
        // Se UserService verifica l'utente, genera JWT token
        String token = JWT.create()
                .withSubject(loginRequest.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 600000)) // 10 minuti di validità
                .sign(Algorithm.HMAC256("secret")); // Utilizza una chiave segreta più sicura in un ambiente reale

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", URLDecoder.decode(redirectUri, StandardCharsets.UTF_8) + "?token=" + token);
        return ResponseEntity.status(HttpStatus.FOUND).body("found");
        // return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }


    // This method is called when the user is not successfully authenticated
    @Operation(summary = "Login failed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Login failed"),
    })
    @GetMapping("/failureLogin")
    public ResponseEntity<String> failureLogin() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login fallito");
    }

    @Operation(summary = "Logout")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirect to the client"),
    })
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, null);
        // TODO: jwt blacklist or invalidation

        String redirectUri = "http://localhost:5173/";

        // redirect to the client
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUri));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
