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
	private final String SECRET_KEY = "secret";
	private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		if (exchange.getRequest().getPath().value().contains("auth") || exchange.getRequest().getPath().value().contains("login")) {
			return chain.filter(exchange);
		}

		try {
			String token = extractJwtFromRequest(exchange.getRequest());
			if (token == null) {
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return exchange.getResponse().setComplete();
			}

			JWT.require(algorithm).build().verify(token);
			return chain.filter(exchange);
		} catch (JWTVerificationException e) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
	}

	private String extractJwtFromRequest(ServerHttpRequest request) {
		List<String> authHeaders = request.getHeaders().get("Authorization");
		if (authHeaders != null && !authHeaders.isEmpty()) {
			String authHeader = authHeaders.get(0);
			String[] parts = authHeader.split(" ");
			if (parts.length == 2 && "Bearer".equals(parts[0])) {
				return parts[1];
			}
		}
		return null;
	}

	@Override
	public int getOrder() {
		return -1;
	}
}