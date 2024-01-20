package com.authentication.authenticationservice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AuthController {
    @GetMapping("/")
    public String home() {
        return "Hello from Authentication Service";
    }

    @GetMapping("/secured")
    public String secured() {
        return "Secured Hello from Authentication Service";
    }

    @GetMapping("/endLogin")
    public Map<String, Object> endLogin(Authentication auth) {
        OAuth2User auth2 = (OAuth2User) auth.getPrincipal();
        return auth2.getAttributes();
    }

}
