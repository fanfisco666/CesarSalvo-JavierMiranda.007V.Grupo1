package com.inmobiliaria.inmobiliaria_visita.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class VisitaRequest {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idCliente;

    @NotNull(message = "El ID de la propiedad es obligatorio")
    private Long idPropiedad;

    @NotNull(message = "El ID del agente es obligatorio")
    private Long idAgente;

    @NotNull(message = "La fecha de visita es obligatoria")
    @Future(message = "La fecha debe ser futura")
    private LocalDate fechaVisita;

    @NotNull(message = "La hora de visita es obligatoria")
    private LocalTime horaVisita;

    @Size(max = 500)
    private String observaciones;
}