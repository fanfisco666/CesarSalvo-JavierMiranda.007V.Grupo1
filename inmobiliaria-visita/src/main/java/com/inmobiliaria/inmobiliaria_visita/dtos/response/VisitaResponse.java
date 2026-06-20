package com.inmobiliaria.inmobiliaria_visita.dtos.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la respuesta de una visita")
@Data
@Builder
public class VisitaResponse {
    @Schema(description = "ID de la visita")
    private Long idVisita;
    @Schema(description = "ID del cliente")
    private Long idCliente;
    @Schema(description = "ID de la propiedad")
    private Long idPropiedad;
    @Schema(description = "ID del agente")
    private Long idAgente;
    @Schema(description = "Fecha de la visita")
    private LocalDate fechaVisita;
    @Schema(description = "Hora de la visita")
    private LocalTime horaVisita;
    @Schema(description = "Estado de la visita")
    private String estado;
    @Schema(description = "Observaciones sobre la visita")
    private String observaciones;
}