package org.example.authservice;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {

    private static final String SECRET_KEY = "mySecretKey123"; // должен совпадать с Gateway

    // валидность токена, например 1 час
    private static final long EXPIRATION_TIME_MS = 3600000;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Если нужно validateToken - можно добавить метод тут:
    //   public boolean validateToken(String token) {...}
}