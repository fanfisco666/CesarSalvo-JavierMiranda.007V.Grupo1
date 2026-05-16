package com.inmobiliaria.inmobiliaria_propiedad.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PropiedadRequest {
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede exceder los 100 caracteres")
    private String titulo;

    @Size(max = 100, message = "La dirección no puede exceder los 100 caracteres")
    private String direccion;

    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 30, message = "El tipo no puede exceder los 30 caracteres")
    private String tipo;

    @NotBlank(message = "El precio es obligatorio")
    @Size(max = 100, message = "El precio no puede exceder los 100 caracteres")
    private Double precio;

    @NotBlank(message = "El estado es obligatorio")
    private boolean estado;

    @NotBlank(message = "La superficie es obligatoria")
    @Size(max = 100, message = "La superficie no puede exceder los 100 caracteres")
    private Double superficie;

    @NotBlank(message = "La cantidad de habitaciones es obligatoria")
    @Size(max = 100, message = "La cantidad de habitaciones no puede exceder los 100 caracteres")
    private Integer habitacion;

    @NotBlank(message = "La cantidad de baños es obligatoria")
    @Size(max = 100, message = "La cantidad de baños no puede exceder los 100 caracteres")
    private Integer banno;

    @Size(max = 300, message = "La descripción no puede exceder los 300 caracteres")
    private String descripcion;

}
