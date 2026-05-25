package com.inmobiliaria.inmobiliaria_mantencion.dtos.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class MantencionResponse {

    private Long idMantencion;
    private String descripcion;
    private String estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private AgenteResponse agente;
    private PropiedadResponse propiedad;
}