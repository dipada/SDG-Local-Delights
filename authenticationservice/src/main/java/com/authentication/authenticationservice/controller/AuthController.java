package com.authentication.authenticationservice.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.status(HttpStatus.OK).body("Benvenuto");
    }

    @GetMapping("/successLogin")
    public ResponseEntity<String> successLogin(Authentication auth){
        OAuth2User user = (OAuth2User) auth.getPrincipal();

        //make jwt token
        String token = JWT.create()
                .withSubject(user.getAttribute("email"))
                .withClaim("name",(String) user.getAttribute("given_name"))
                .withClaim("surname",(String) user.getAttribute("family_name"))
                .withClaim("picture",(String) user.getAttribute("picture"))
                .withExpiresAt(new Date(System.currentTimeMillis() + 600000)) //10 minuti
                .sign(Algorithm.HMAC256("secret"));


        return ResponseEntity.status(HttpStatus.OK).body(token);
    }


    // test token decripting
    @GetMapping("/testDecript")
    public ResponseEntity<String> testDecript(@RequestParam String token){
        String jwt = JWT.require(Algorithm.HMAC256("secret"))
                .build()
                .verify(token)
                .getClaims().toString();
        return ResponseEntity.status(HttpStatus.OK).body(jwt);
    }

    @GetMapping("/failureLogin")
    public ResponseEntity<String> failureLogin(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login fallito");
    }

}
