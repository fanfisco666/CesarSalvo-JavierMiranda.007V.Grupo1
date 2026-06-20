package com.inmobiliaria.inmobiliaria_visita.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la creación de una visita a una propiedad")
@Data
public class VisitaRequest {

    @Schema(description = "ID del cliente")
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idCliente;

    @Schema(description = "ID de la propiedad")
    @NotNull(message = "El ID de la propiedad es obligatorio")
    private Long idPropiedad;

    @Schema(description = "ID del agente")
    @NotNull(message = "El ID del agente es obligatorio")
    private Long idAgente;

    @Schema(description = "Fecha de visita")
    @NotNull(message = "La fecha de visita es obligatoria")
    @Future(message = "La fecha debe ser futura")
    private LocalDate fechaVisita;

    @Schema(description = "Hora de visita")
    @NotNull(message = "La hora de visita es obligatoria")
    private LocalTime horaVisita;

    @Schema(description = "Observaciones sobre la visita")
    @Size(max = 500)
    private String observaciones;
}