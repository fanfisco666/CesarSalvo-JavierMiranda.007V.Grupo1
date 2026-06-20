package com.inmobiliaria.inmobiliaria_mantencion.dtos.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la respuesta de una mantención")
@Data
@Builder
public class MantencionResponse {

    @Schema(description = "ID de la mantención")
    private Long idMantencion;
    @Schema(description = "Descripción de la mantención")
    private String descripcion;
    @Schema(description = "Estado de la mantención")
    private String estado;
    @Schema(description = "Fecha de inicio de la mantención")
    private LocalDate fechaInicio;
    @Schema(description = "Fecha de finalización de la mantención")
    private LocalDate fechaFin;
    @Schema(description = "Agente asignado a la mantención")
    private AgenteResponse agente;
    @Schema(description = "Propiedad relacionada con la mantención")
    private PropiedadResponse propiedad;
}