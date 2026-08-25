package com.taskflow.gateway.AuthenticationFilter;

import java.nio.charset.StandardCharsets;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.common.jwt.service.JwtService;
import com.taskflow.gateway.util.RouteValidator;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final RouteValidator routeValidator;
    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        var request = exchange.getRequest();
        
        log.info("inside auth filter'.."+request);

        // Skip public APIs
        if (!routeValidator.isSecured.test(request)) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing Authorization Header");
        }

        String token = authHeader.substring(7);

        try {

            String username = jwtService.extractUsername(token);

            String userId = jwtService.extractUserId(token);

            if (!jwtService.isTokenValid(token, username)) {
                return unauthorized(exchange, "Invalid Access Token");
            }

            var mutatedRequest = request.mutate()
                    .header("X-User-Id", userId.toString())
                    .header("X-Username", username)
                    .build();

            return chain.filter(exchange.mutate()
                    .request(mutatedRequest)
                    .build());

        } catch (ExpiredJwtException ex) {

            return unauthorized(exchange, "Access Token Expired");

        } catch (JwtException ex) {

            return unauthorized(exchange, "Invalid Access Token");

        } catch (Exception ex) {

            return unauthorized(exchange, "Authentication Failed");
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String body = """
                {
                  "status":401,
                  "error":"Unauthorized",
                  "message":"%s"
                }
                """.formatted(message);

        var buffer = exchange.getResponse()
                .bufferFactory()
                .wrap(body.getBytes(StandardCharsets.UTF_8));

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}