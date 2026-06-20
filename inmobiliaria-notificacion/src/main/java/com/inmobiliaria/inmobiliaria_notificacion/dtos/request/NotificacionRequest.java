package com.inmobiliaria.inmobiliaria_notificacion.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Schema(description = "DTO para la creación de una notificación")
@Data
public class NotificacionRequest {

    @NotNull(message = "El ID del cliente es obligatorio")
    @Schema(description = "ID del cliente")
    private Long idCliente;

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "VISITA_AGENDADA|CONTRATO_CREADO|PAGO_PENDIENTE|PAGO_CONFIRMADO|VISITA_CANCELADA|CONTRATO_VENCIDO", message = "Tipo no válido")
    @Schema(description = "Tipo de notificación")
    private String tipo;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(max = 500)
    @Schema(description = "Mensaje de la notificación")
    private String mensaje;

    @Schema(description = "Referencia asociada a la notificación")
    private String referencia;
}