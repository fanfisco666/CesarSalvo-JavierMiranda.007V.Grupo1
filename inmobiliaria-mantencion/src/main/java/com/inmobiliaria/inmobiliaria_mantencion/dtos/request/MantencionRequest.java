package com.inmobiliaria.inmobiliaria_mantencion.dtos.request;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "DTO para la creación de una mantención")
@Data
public class MantencionRequest {

    @Schema(description = "Descripción de la mantención")
    @NotBlank(message = "La descripción de la mantención es obligatoria")
    private String descripcion;

    @Schema(description = "Estado de la mantención")
    @NotBlank(message = "El estado de la mantención es obligatorio")
    private String estado; // Puede ser "pendiente", "en progreso", "completado"

    @NotNull(message = "La fecha de inicio de la mantención es obligatoria")
    @Schema(description = "Fecha de inicio de la mantención")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de finalización de la mantención")
    private LocalDate fechaFin; // puede ser null si la mantención no ha finalizado

    @NotNull(message = "El ID del agente es obligatorio")
    @Schema(description = "ID del agente asignado a la mantención")
    private Long idAgente; // id del agente inmobiliario asignado a la mantención

    @NotNull(message = "El ID de la propiedad es obligatorio")
    @Schema(description = "ID de la propiedad relacionada con la mantención")
    private Long idPropiedad; // id de la propiedad relacionada con la mantención

}
