package com.inmobiliaria.inmobiliaria_visita.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO para la creación de una notificación desde el servicio de visita")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionRequest {
    @Schema(description = "ID del cliente")
    private Long idCliente;
    @Schema(description = "Tipo de notificación")
    private String tipo;
    @Schema(description = "Mensaje de la notificación")
    private String mensaje;
    @Schema(description = "Referencia de la notificación")
    private String referencia;
}