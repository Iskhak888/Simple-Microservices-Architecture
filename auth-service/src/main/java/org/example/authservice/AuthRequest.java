package org.example.authservice;

import lombok.*;

@Getter
@Setter
public class AuthRequest {
    private String username;
    private String password;
}