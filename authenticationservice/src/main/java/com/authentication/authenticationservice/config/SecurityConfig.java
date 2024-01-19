package com.authentication.authenticationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

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
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> oauth2
                        //.loginPage("/login")
                        //.loginPage("http://localhost:8081/api/v1/user/seller/daniele@gmail.com")
                        .defaultSuccessUrl("https://www.youtube.com/watch?v=KSXdqmCusmY&ab_channel=YellowFellow", true)
                        //.successHandler((request, response, authentication) -> {
                        //  response.sendRedirect("https://www.youtube.com/watch?v=KSXdqmCusmY&ab_channel=YellowFellow");
                        //})
                )
        .build();
    }
}




