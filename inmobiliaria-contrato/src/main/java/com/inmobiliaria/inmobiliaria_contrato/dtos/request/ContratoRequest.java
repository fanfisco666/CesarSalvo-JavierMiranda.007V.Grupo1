package com.inmobiliaria.inmobiliaria_contrato.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la creación de un contrato de arriendo o venta")
@Data
public class ContratoRequest {

    @Schema(description = "ID del cliente")
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idCliente;

    @Schema(description = "ID de la propiedad")
    @NotNull(message = "El ID de la propiedad es obligatorio")
    private Long idPropiedad;

    @Schema(description = "ID del agente")
    @NotNull(message = "El ID del agente es obligatorio")
    private Long idAgente;

    @Schema(description = "ID de la visita")
    @NotNull(message = "El ID de la visita es obligatorio")
    private Long idVisita;

    @Schema(description = "Tipo de contrato")
    @NotBlank(message = "El tipo de contrato es obligatorio")
    @Pattern(regexp = "ARRIENDO|VENTA", message = "El tipo debe ser ARRIENDO o VENTA")
    private String tipoContrato;

    @Schema(description = "Fecha de inicio")
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de fin")
    private LocalDate fechaFin;

    @Schema(description = "Observaciones")
    @Size(max = 500)
    private String observaciones;
}