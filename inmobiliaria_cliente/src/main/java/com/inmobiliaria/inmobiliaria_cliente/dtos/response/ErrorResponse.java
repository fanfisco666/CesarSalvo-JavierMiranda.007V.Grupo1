package com.inmobiliaria.inmobiliaria_cliente.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la respuesta de un error desde el servicio de cliente")
@Data
@Builder
public class ErrorResponse {
    @Schema(description = "Estado HTTP")
    private int status;
    @Schema(description = "Tipo de error")
    private String error;
    @Schema(description = "Mensaje de error")
    private String message;
    @Schema(description = "Ruta de la solicitud que causó el error")
    private String path;
    @Schema(description = "Marca de tiempo del error")
    private LocalDateTime timestamp;
    @Schema(description = "Detalles adicionales del error")
    private Map<String, String> details;
}
