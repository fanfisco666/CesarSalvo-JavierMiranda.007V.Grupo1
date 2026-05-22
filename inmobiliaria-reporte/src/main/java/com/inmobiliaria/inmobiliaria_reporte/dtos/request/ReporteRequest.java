package com.inmobiliaria.inmobiliaria_reporte.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReporteRequest {

    @NotBlank(message = "El título del reporte es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripción del reporte es obligatoria")
    @Size(max = 1000, message = "La descripción no puede exceder los 1000 caracteres")
    private String descripcion;

    @NotNull(message = "El ID de la propiedad es obligatorio")
    private Long idPropiedad;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El ID del agente es obligatorio")
    private Long idAgente;

    @NotBlank(message = "El tipo de reporte es obligatorio")
    private String tipoReporte; // Puede ser mantenimiento, queja.

}
