package com.authentication.authenticationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/*
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests( auth -> {auth.requestMatchers("/").permitAll();
                        auth.anyRequest().authenticated();
                })
                .oauth2Login(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .build();



    }
    */


    /*
    .formLogin(form -> form
                        .loginPage("http://localhost:8081/api/v1/user/seller/daniele@gmail.com")
                        .permitAll()
     */



@Configuration
@EnableWebSecurity
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

         http
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/", "/auth/welcome").permitAll();
                            authorize.anyRequest().authenticated();
                })

            .oauth2Login(oauth2 -> oauth2
                //.loginPage("https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=779387750362-dt8b5nnpj0s13r3mi2qs13b6gal9ir7b.apps.googleusercontent.com&redirect_uri=http://localhost:8080/login/oauth2/code/google&scope=openid%20profile%20email")
                    //.loginProcessingUrl("https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=779387750362-dt8b5nnpj0s13r3mi2qs13b6gal9ir7b.apps.googleusercontent.com&redirect_uri=http://localhost:8080/login/oauth2/code/google&scope=openid%20profile%20email&state=1")
                    .loginPage("/oauth2/authorization/google")
                .defaultSuccessUrl("http://localhost:8080/auth/successLogin", true)
                .failureUrl("http://localhost:8080/auth/failureLogin")

        );
       return http.build();
    }
}




