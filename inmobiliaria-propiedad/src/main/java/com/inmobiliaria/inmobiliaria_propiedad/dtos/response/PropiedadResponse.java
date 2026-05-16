package com.inmobiliaria.inmobiliaria_propiedad.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PropiedadResponse {

    private Long idPropiedad;
    private String titulo;
    private String direccion;
    private String tipo;
    private Double precio;
    private boolean estado;
    private Double superficie;
    private Integer habitacion;
    private Integer banno;
    private String descripcion;

}
