package com.inmobiliaria.inmobiliaria_reporte.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "DTO para la respuesta de un reporte de propiedad")
@Data
@Builder
public class ReporteResponse {

    @Schema(description = "ID del reporte")
    private Long idReporte;
    @Schema(description = "Título del reporte")
    private String titulo;
    @Schema(description = "Descripción del reporte")
    private String descripcion;
    @Schema(description = "Tipo de reporte")
    private String tipoReporte; // Puede ser mantenimiento, queja.

    // los response de los microservicios.
    @Schema(description = "Propiedad asociada al reporte")
    private PropiedadResponse propiedad;
    @Schema(description = "Cliente asociado al reporte")
    private ClienteResponse cliente;
    @Schema(description = "Agente asociado al reporte")
    private AgenteResponse agente;

}
