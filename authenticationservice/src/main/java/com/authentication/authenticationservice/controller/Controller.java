package com.authentication.authenticationservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/")
    public String home() {
        return "Hello from Authentication Service";
    }

    @GetMapping("/secured")
    public String secured() {
        return "Secured Hello from Authentication Service";
    }

}
