package com.inmobiliaria.inmobiliaria_auth.services;

import com.inmobiliaria.inmobiliaria_auth.dtos.request.LoginRequest;
import com.inmobiliaria.inmobiliaria_auth.dtos.request.RegisterRequest;
import com.inmobiliaria.inmobiliaria_auth.dtos.response.AuthResponse;
import com.inmobiliaria.inmobiliaria_auth.exceptions.AuthException;
import com.inmobiliaria.inmobiliaria_auth.models.UsuarioModel;
import com.inmobiliaria.inmobiliaria_auth.repositories.UsuarioRepository;
import com.inmobiliaria.inmobiliaria_auth.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - AuthService")
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private UsuarioModel usuario;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioModel();
        usuario.setIdUsuario(1L);
        usuario.setUsername("admin");
        usuario.setPassword("admin123");
        usuario.setRol("ADMIN");
        usuario.setActivo(true);

        loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("admin123");
    }

    @Test
    @DisplayName("login - retorna token cuando credenciales son correctas")
    void login_deberiaRetornarTokenConCredencialesCorrectas() {
        // Given
        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.of(usuario));
        when(jwtService.generarToken(anyString(), anyString())).thenReturn("token.jwt.test");

        // When
        AuthResponse resultado = authService.login(loginRequest);

        // Then
        assertThat(resultado.getToken()).isEqualTo("token.jwt.test");
        assertThat(resultado.getUsername()).isEqualTo("admin");
        assertThat(resultado.getRol()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("login - lanza AuthException cuando usuario no existe")
    void login_deberiaLanzarExcepcionCuandoUsuarioNoExiste() {
        // Given
        when(usuarioRepository.findByUsername("noexiste")).thenReturn(Optional.empty());
        loginRequest.setUsername("noexiste");

        // When / Then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("Credenciales incorrectas");
    }

    @Test
    @DisplayName("login - lanza AuthException cuando contraseña es incorrecta")
    void login_deberiaLanzarExcepcionCuandoPasswordIncorrecta() {
        // Given
        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.of(usuario));
        loginRequest.setPassword("wrongpassword");

        // When / Then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("Credenciales incorrectas");
    }

    @Test
    @DisplayName("login - lanza AuthException cuando usuario está inactivo")
    void login_deberiaLanzarExcepcionCuandoUsuarioInactivo() {
        // Given
        usuario.setActivo(false);
        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.of(usuario));

        // When / Then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("inactivo");
    }

    @Test
    @DisplayName("register - registra usuario nuevo correctamente")
    void register_deberiaRegistrarUsuarioNuevo() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("nuevo");
        registerRequest.setPassword("pass123");
        registerRequest.setRol("AGENTE");

        UsuarioModel nuevoUsuario = new UsuarioModel();
        nuevoUsuario.setIdUsuario(2L);
        nuevoUsuario.setUsername("nuevo");
        nuevoUsuario.setPassword("pass123");
        nuevoUsuario.setRol("AGENTE");
        nuevoUsuario.setActivo(true);

        when(usuarioRepository.existsByUsername("nuevo")).thenReturn(false);
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(nuevoUsuario);
        when(jwtService.generarToken(anyString(), anyString())).thenReturn("token.nuevo");

        // When
        AuthResponse resultado = authService.register(registerRequest);

        // Then
        assertThat(resultado.getUsername()).isEqualTo("nuevo");
        assertThat(resultado.getRol()).isEqualTo("AGENTE");
        verify(usuarioRepository, times(1)).save(any(UsuarioModel.class));
    }

    @Test
    @DisplayName("register - lanza AuthException cuando username ya existe")
    void register_deberiaLanzarExcepcionCuandoUsernameExiste() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("admin");
        registerRequest.setPassword("pass123");

        when(usuarioRepository.existsByUsername("admin")).thenReturn(true);

        // When / Then
        assertThatThrownBy(() -> authService.register(registerRequest))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("username");
    }
}
