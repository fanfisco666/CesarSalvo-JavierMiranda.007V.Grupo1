package com.inmobiliaria.inmobiliaria_visita.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionRequest {
    private Long idCliente;
    private String tipo;
    private String mensaje;
    private String referencia;
}