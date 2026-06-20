package com.inmobiliaria.inmobiliaria_auth.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "Request para iniciar sesión")
@Data
public class LoginRequest {

    @Schema(description = "Nombre de usuario")
    @NotBlank(message = "El usuario es obligatorio")
    private String username;

    @Schema(description = "Contraseña")
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
