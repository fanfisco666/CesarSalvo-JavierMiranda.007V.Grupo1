package com.inmobiliaria.inmobiliaria_auth.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "Request para registrar un nuevo usuario")
@Data
public class RegisterRequest {

    @Schema(description = "Nombre de usuario")
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, max = 100, message = "El minimo de caracteres es 4 y maximo 100")
    private String username;

    @Schema(description = "Contraseña")
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña tiene que tener minimo 6 caracteres")
    private String password;

    @Schema(description = "Rol del usuario")
    private String rol;
}
