package org.example.gatewayservice;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    // Путь, куда отправляемся за токеном (или публичные пути)
    private static final List<String> PUBLIC_PATHS = Arrays.asList("/auth/login", "/auth/register", "/public/hello");

    // Условный секрет (должен быть одинаковый с Auth-сервисом, если мы валидируем локально)
    private static final String SECRET_KEY = "mySecretKey123";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 1) Проверяем, является ли запрос публичным
        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        // 2) Иначе смотрим заголовок Authorization
        String token = extractBearerToken(exchange.getRequest());
        if (token == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 3) Валидируем JWT (локально или через Auth-сервис).
        if (!validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 4) Всё ок — пропускаем запрос к main-service
        return chain.filter(exchange);
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

    private String extractBearerToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            // Распарсим JWT
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

            // Если сигнатура и срок действия ок — токен валиден
            return true;
        } catch (SignatureException e) {
            // Подпись не верна
            return false;
        } catch (Exception e) {
            // Истёк срок действия или другие проблемы
            return false;
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
