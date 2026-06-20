package com.inmobiliaria.inmobiliaria_propiedad.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "DTO para la creación o actualización de una propiedad")
@Data
public class PropiedadRequest {

    @Schema(description = "Título de la propiedad", example = "Casa en venta en el centro")
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 255, message = "El título no puede exceder los 255 caracteres")
    private String titulo;

    @Schema(description = "Dirección de la propiedad", example = "Calle Principal 123")
    @Size(max = 255, message = "La dirección no puede exceder los 255 caracteres")
    private String direccion;

    @Schema(description = "Tipo de la propiedad", example = "Casa")
    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 255, message = "El tipo no puede exceder los 255 caracteres")
    private String tipo;

    @Schema(description = "Precio de la propiedad", example = "500000.0")
    @NotNull(message = "El precio es obligatorio")
    private Double precio;

    @Schema(description = "Estado de la propiedad", example = "true")
    private boolean estado = true;

    @Schema(description = "Superficie de la propiedad", example = "200.0")
    @NotNull(message = "La superficie es obligatoria")
    private Double superficie;

    @Schema(description = "Cantidad de habitaciones", example = "3")
    @NotNull(message = "La cantidad de habitaciones es obligatoria")
    private Integer habitacion;

    @Schema(description = "Cantidad de baños", example = "2")
    @NotNull(message = "La cantidad de baños es obligatoria")
    private Integer banno;

    @Schema(description = "Descripción de la propiedad", example = "Hermosa casa en venta en el centro de la ciudad")
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;
}
