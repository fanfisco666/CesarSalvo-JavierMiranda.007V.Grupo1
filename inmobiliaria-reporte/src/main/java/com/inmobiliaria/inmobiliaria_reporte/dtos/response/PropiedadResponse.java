package com.inmobiliaria.inmobiliaria_reporte.dtos.response;

import lombok.Data;

@Data
public class PropiedadResponse {

    private Long idPropiedad;
    private String direccion;
    private String tipoPropiedad; // casa, departamento, oficina.

}
