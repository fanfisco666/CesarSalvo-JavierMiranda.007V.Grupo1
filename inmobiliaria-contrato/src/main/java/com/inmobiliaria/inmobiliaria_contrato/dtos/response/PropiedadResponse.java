package com.inmobiliaria.inmobiliaria_contrato.dtos.response;

import lombok.Data;

@Data
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