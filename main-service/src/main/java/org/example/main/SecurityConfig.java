package org.example.main;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.ServletException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Пример: Отключить csrf, разрешить /public/** без авторизации,
        // для всего остального требовать токен
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // Простой фильтр, чтобы показать идею; в реальности можно делать ResourceServer
    class JwtFilter extends OncePerRequestFilter {
        private static final String SECRET_KEY = "mySecretKey123";

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain) throws ServletException, IOException {

            String path = request.getRequestURI();
            if (path.startsWith("/public")) {
                // Публичный - пропускаем
                filterChain.doFilter(request, response);
                return;
            }

            // Иначе проверяем заголовок
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            String token = authHeader.substring(7);
            try {
                Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
                // валидный токен
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }
    }
}
