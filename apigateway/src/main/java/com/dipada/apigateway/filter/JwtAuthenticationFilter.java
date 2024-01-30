
package com.dipada.apigateway.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final Algorithm algorithm = Algorithm.HMAC256("secret"); // Utilizza la stessa chiave segreta

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (exchange.getRequest().getPath().value().contains("auth") || exchange.getRequest().getPath().value().contains("login")) {
            return chain.filter(exchange);
        }

        try {
            System.out.println("FILTRO JWT + " + exchange.getRequest().getHeaders());
            // Estrai il token JWT dall'header Authorization
            String token = extractJwtFromRequest(exchange.getRequest());
            System.out.println("TOKEN UTENTE: " + token);

            // Se il token non è presente, rispondi con UNAUTHORIZED
            if (token == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
                //URI uri = URI.create("lb://authenticationservice/auth/login");
                //ServerHttpRequest request = exchange.getRequest().mutate().uri(uri).build();
                //return chain.filter(exchange.mutate().request(request).build());
            }

            // Verifica il token JWT
            JWT.require(algorithm).build().verify(token);

            // Se il token è valido, continua con la catena di filtri
            return chain.filter(exchange);
        } catch (JWTVerificationException e) {
            // Se la verifica fallisce, imposta lo stato della risposta su UNAUTHORIZED
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    private String extractJwtFromRequest(ServerHttpRequest request) {
        // Estrai il token JWT dall'header Authorization
        List<String> authHeaders = request.getHeaders().get("Authorization");
        if (authHeaders != null && !authHeaders.isEmpty()) {
            String authHeader = authHeaders.get(0);
            String[] parts = authHeader.split(" ");
            if (parts.length == 2 && "Bearer".equals(parts[0])) {
                return parts[1]; // Restituisce il token
            }
        }
        return null; // Nessun token presente
    }

    @Override
    public int getOrder() {
        return -1; // Ordine di esecuzione del filtro
    }
}