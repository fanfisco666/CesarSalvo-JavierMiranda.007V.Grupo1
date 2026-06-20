package com.inmobiliaria.inmobiliaria_notificacion.dtos.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la respuesta de una notificación")
@Data
@Builder
public class NotificacionResponse {
    @Schema(description = "ID de la notificación")
    private Long idNotificacion;
    @Schema(description = "ID del cliente")
    private Long idCliente;
    @Schema(description = "Tipo de notificación")
    private String tipo;
    @Schema(description = "Mensaje de la notificación")
    private String mensaje;
    @Schema(description = "Fecha de envío de la notificación")
    private LocalDateTime fechaEnvio;
    @Schema(description = "Indica si la notificación ha sido leída")
    private Boolean leida;
    @Schema(description = "Referencia asociada a la notificación")
    private String referencia;
}