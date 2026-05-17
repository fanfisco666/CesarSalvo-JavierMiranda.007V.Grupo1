package com.inmobiliaria.inmobiliaria_notificacion.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class NotificacionRequest {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idCliente;

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "VISITA_AGENDADA|CONTRATO_CREADO|PAGO_PENDIENTE|PAGO_CONFIRMADO|VISITA_CANCELADA|CONTRATO_VENCIDO",
            message = "Tipo no válido")
    private String tipo;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(max = 500)
    private String mensaje;

    private String referencia;
}