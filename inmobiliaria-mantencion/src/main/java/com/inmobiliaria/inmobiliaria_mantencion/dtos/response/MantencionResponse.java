package com.inmobiliaria.inmobiliaria_mantencion.dtos.response;

import java.time.LocalDate;

import com.inmobiliaria.inmobiliaria_mantencion.dtos.external.AgenteResponse;
import com.inmobiliaria.inmobiliaria_mantencion.dtos.external.PropiedadResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MantencionResponse {

    private Long idMantencion;
    private String descripcion;
    private String estado; // Puede ser "pendiente", "en progreso", "completado"
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    // informacion que vienne de otros microservicios
    private AgenteResponse agente; // ID del agente inmobiliario asignado a la mantención
    private PropiedadResponse propiedad; // ID de la propiedad relacionada con la mantención

}
