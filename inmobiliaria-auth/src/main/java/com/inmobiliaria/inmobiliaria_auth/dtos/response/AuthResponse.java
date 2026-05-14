package com.inmobiliaria.inmobiliaria_auth.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String username;
    private String rol;
    private String mensaje;
}
