package org.example.authservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // @RestController уже возвращает JSON, так что @ResponseBody не нужен
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        System.out.println("rabotaet");
        // 1) Проверяем логин/пароль (в примере — хардкод)
        if ("admin".equals(request.getUsername()) && "123".equals(request.getPassword())) {
            // 2) Генерируем токен
            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    // (Опционально) /auth/register, /auth/validate, /auth/me и т.д.
}
