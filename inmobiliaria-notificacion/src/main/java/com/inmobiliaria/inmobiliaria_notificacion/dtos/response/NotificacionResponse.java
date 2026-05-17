package com.inmobiliaria.inmobiliaria_notificacion.dtos.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class NotificacionResponse {
    private Long idNotificacion;
    private Long idCliente;
    private String tipo;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private Boolean leida;
    private String referencia;
}