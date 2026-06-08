package com.inmobiliaria.inmobiliaria_auth.controllers;

import com.inmobiliaria.inmobiliaria_auth.dtos.request.LoginRequest;
import com.inmobiliaria.inmobiliaria_auth.dtos.request.RegisterRequest;
import com.inmobiliaria.inmobiliaria_auth.dtos.response.AuthResponse;
import com.inmobiliaria.inmobiliaria_auth.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
