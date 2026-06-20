package com.inmobiliaria.inmobiliaria_auth.controllers;

import com.inmobiliaria.inmobiliaria_auth.dtos.request.LoginRequest;
import com.inmobiliaria.inmobiliaria_auth.dtos.request.RegisterRequest;
import com.inmobiliaria.inmobiliaria_auth.dtos.response.AuthResponse;
import com.inmobiliaria.inmobiliaria_auth.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticación", description = "Operaciones relacionadas con autenticación y registro de usuarios en el sistema inmobiliario")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Iniciar sesión", description = "Permite a un usuario iniciar sesión en el sistema proporcionando sus credenciales")
    @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Registrar usuario", description = "Permite a un nuevo usuario registrarse en el sistema")
    @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
