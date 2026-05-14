package com.inmobiliaria.inmobiliaria_auth.services;

import com.inmobiliaria.inmobiliaria_auth.dtos.request.LoginRequest;
import com.inmobiliaria.inmobiliaria_auth.dtos.request.RegisterRequest;
import com.inmobiliaria.inmobiliaria_auth.dtos.response.AuthResponse;
import com.inmobiliaria.inmobiliaria_auth.exceptions.AuthException;
import com.inmobiliaria.inmobiliaria_auth.models.UsuarioModel;
import com.inmobiliaria.inmobiliaria_auth.repositories.UsuarioRepository;
import com.inmobiliaria.inmobiliaria_auth.security.JwtService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    public AuthResponse login(LoginRequest request) {
        log.info("Intento de login para usuario: {}", request.getUsername());

        UsuarioModel usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    log.warn("Login fallido - usuario no encontrado: {}", request.getUsername());
                    return new AuthException("Credenciales incorrectas");
                });

        if (!usuario.getActivo()) {
            log.warn("Login fallido - usuario inactivo: {}", request.getUsername());
            throw new AuthException("Usuario inactivo");
        }

        if (!request.getPassword().equals(usuario.getPassword())) {
            log.warn("Login fallido - contraseña incorrecta para usuario: {}", request.getUsername());
            throw new AuthException("Credenciales incorrectas");
        }

        String token = jwtService.generarToken(usuario.getUsername(), usuario.getRol());
        log.info("Login exitoso para usuario: {} con rol: {}", usuario.getUsername(), usuario.getRol());

        return AuthResponse.builder()
                .token(token)
                .username(usuario.getUsername())
                .rol(usuario.getRol())
                .mensaje("Login exitoso")
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        log.info("Registrando nuevo usuario: {}", request.getUsername());

        if (usuarioRepository.existsByUsername(request.getUsername())) {
            log.warn("Registro fallido - username ya existe: {}", request.getUsername());
            throw new AuthException("El username ya está en uso");
        }

        UsuarioModel usuario = new UsuarioModel();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(request.getPassword());
        usuario.setRol(request.getRol());
        usuario.setActivo(true);

        usuarioRepository.save(usuario);
        log.info("Usuario registrado exitosamente: {} con rol: {}", usuario.getUsername(), usuario.getRol());

        String token = jwtService.generarToken(usuario.getUsername(), usuario.getRol());

        return AuthResponse.builder()
                .token(token)
                .username(usuario.getUsername())
                .rol(usuario.getRol())
                .mensaje("Usuario registrado exitosamente")
                .build();
    }
}