package com.authentication.authenticationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@EnableWebSecurity
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authorize) -> {
            authorize.requestMatchers("/", "/auth/welcome").permitAll();
            authorize.anyRequest().authenticated();
        }).oauth2Login(oauth2 -> oauth2.loginPage("/oauth2/authorization/google").defaultSuccessUrl("http://localhost:8080/auth/successLogin", true).failureUrl("http://localhost:8080/auth/failureLogin")).logout(logout -> logout.logoutUrl("/auth/logout").logoutSuccessUrl("/auth/logout"));
        return http.build();
    }
}




