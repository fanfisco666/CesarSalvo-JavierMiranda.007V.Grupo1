package com.inmobiliaria.inmobiliaria_auth.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, max = 100, message = "El minimo de caracteres es 4 y maximo 100")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña tiene que tener minimo 6 caracteres")
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(regexp = "ADMIN|AGENTE|CLIENTE", message = "El rol debe ser ADMIN, AGENTE o CLIENTE")
    private String rol;
}
