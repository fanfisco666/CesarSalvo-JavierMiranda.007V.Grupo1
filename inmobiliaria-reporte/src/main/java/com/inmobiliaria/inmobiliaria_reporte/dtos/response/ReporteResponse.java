package com.inmobiliaria.inmobiliaria_reporte.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReporteResponse {

    private Long idReporte;
    private String titulo;
    private String descripcion;
    private String tipoReporte; // Puede ser mantenimiento, queja.

    // los response de los microservicios.
    private PropiedadResponse propiedad;
    private ClienteResponse cliente;
    private AgenteResponse agente;

}
