package com.authentication.authenticationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/", "/auth/signup", "/auth/failureLogin", "/auth/customLogin", "/auth/logout").permitAll()
                            .anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/google")
                        .defaultSuccessUrl("/auth/google", true)
                        .failureUrl("/auth/failureLogin")
                ).logout(logout -> logout.logoutUrl("/auth/logout/entry").logoutSuccessUrl("/auth/logout"));
        return http.build();
    }
}