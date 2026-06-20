package com.inmobiliaria.inmobiliaria_mantencion.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO para la respuesta de un agente")
@Data
public class AgenteResponse {
    @Schema(description = "ID del agente")
    private Long idAgente;
    @Schema(description = "RUT del agente")
    private String rutAgente;
    @Schema(description = "Nombre del agente")
    private String nombreAgente;
    @Schema(description = "Apellidos del agente")
    private String apellidosAgente;
    @Schema(description = "Correo electrónico del agente")
    private String correoAgente;

}
