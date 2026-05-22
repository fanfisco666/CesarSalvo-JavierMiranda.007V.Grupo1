package com.inmobiliaria.inmobiliaria_mantencion.dtos.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MantencionRequest {

    @NotBlank(message = "La descripción de la mantención es obligatoria")
    private String descripcion;

    @NotBlank(message = "El estado de la mantención es obligatorio")
    private String estado; // Puede ser "pendiente", "en progreso", "completado"

    @NotNull(message = "La fecha de inicio de la mantención es obligatoria")
    private LocalDate fechaInicio;

    private LocalDate fechaFin; // puede ser null si la mantención no ha finalizado

    @NotNull(message = "El ID del agente es obligatorio")
    private Long idAgente; // id del agente inmobiliario asignado a la mantención

    @NotNull(message = "El ID de la propiedad es obligatorio")
    private Long idPropiedad; // id de la propiedad relacionada con la mantención

}
