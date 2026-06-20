package com.inmobiliaria.inmobiliaria_auth.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "Response para autenticación de usuario")
@Data
@Builder
public class AuthResponse {
    @Schema(description = "Token de autenticación")
    private String token;
    @Schema(description = "Nombre de usuario")
    private String username;
    @Schema(description = "Rol del usuario")
    private String rol;
    @Schema(description = "Mensaje de respuesta")
    private String mensaje;
}
