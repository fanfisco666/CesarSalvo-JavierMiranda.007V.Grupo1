package com.inmobiliaria.inmobiliaria_propiedad.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PropiedadRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 255, message = "El título no puede exceder los 255 caracteres")
    private String titulo;

    @Size(max = 255, message = "La dirección no puede exceder los 255 caracteres")
    private String direccion;

    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 255, message = "El tipo no puede exceder los 255 caracteres")
    private String tipo;

    @NotNull(message = "El precio es obligatorio")
    private Double precio;

    private boolean estado = true;

    @NotNull(message = "La superficie es obligatoria")
    private Double superficie;

    @NotNull(message = "La cantidad de habitaciones es obligatoria")
    private Integer habitacion;

    @NotNull(message = "La cantidad de baños es obligatoria")
    private Integer banno;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;
}
