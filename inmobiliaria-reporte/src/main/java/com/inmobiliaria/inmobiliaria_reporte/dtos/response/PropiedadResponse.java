package com.inmobiliaria.inmobiliaria_reporte.dtos.response;

import lombok.Data;

@Schema(description = "DTO para la respuesta de una propiedad")
@Data
public class PropiedadResponse {
    @Schema(description = "ID de la propiedad")
    private Long idPropiedad;
    @Schema(description = "Título de la propiedad")
    private String titulo;
    @Schema(description = "Dirección de la propiedad")
    private String direccion;
    @Schema(description = "Tipo de la propiedad")
    private String tipo;
    @Schema(description = "Precio de la propiedad")
    private Double precio;
    @Schema(description = "Estado de la propiedad")
    private boolean estado;
    @Schema(description = "Superficie de la propiedad")
    private Double superficie;
    @Schema(description = "Número de habitaciones de la propiedad")
    private Integer habitacion;
    @Schema(description = "Número de baños de la propiedad")
    private Integer banno;
    @Schema(description = "Descripción de la propiedad")
    private String descripcion;
}