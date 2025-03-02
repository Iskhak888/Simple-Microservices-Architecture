package org.example.authservice;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {

    // Длина ключа >= 32 символов (256 бит), чтобы избежать WeakKeyException
    private static final String SECRET_KEY = "sR6+u3zA1M9shzJkGRjDmg+6nD7WQkxgMhpL0w==123456789987654321qwerasdf";

    // Валидность токена, например, 1 час
    private static final long EXPIRATION_TIME_MS = 3600000;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Можно добавить метод validateToken(...) при необходимости
}
