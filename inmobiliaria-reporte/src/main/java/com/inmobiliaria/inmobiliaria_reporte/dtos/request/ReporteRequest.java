package com.inmobiliaria.inmobiliaria_reporte.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la creación de un reporte de propiedad")
@Data
public class ReporteRequest {

    @NotBlank(message = "El título del reporte es obligatorio")
    @Schema(description = "Título del reporte")
    private String titulo;

    @NotBlank(message = "La descripción del reporte es obligatoria")
    @Size(max = 1000, message = "La descripción no puede exceder los 1000 caracteres")
    @Schema(description = "Descripción del reporte")
    private String descripcion;

    @NotNull(message = "El ID de la propiedad es obligatorio")
    @Schema(description = "ID de la propiedad")
    private Long idPropiedad;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Schema(description = "ID del usuario")
    private Long idUsuario;

    @NotNull(message = "El ID del agente es obligatorio")
    @Schema(description = "ID del agente")
    private Long idAgente;

    @NotBlank(message = "El tipo de reporte es obligatorio")
    @Schema(description = "Tipo de reporte")
    private String tipoReporte; // Puede ser mantenimiento, queja.

}
