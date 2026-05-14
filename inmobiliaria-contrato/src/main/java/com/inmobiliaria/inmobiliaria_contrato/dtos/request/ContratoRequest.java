package com.inmobiliaria.inmobiliaria_contrato.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ContratoRequest {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idCliente;

    @NotNull(message = "El ID de la propiedad es obligatorio")
    private Long idPropiedad;

    @NotNull(message = "El ID del agente es obligatorio")
    private Long idAgente;

    @NotNull(message = "El ID de la visita es obligatorio")
    private Long idVisita;

    @NotBlank(message = "El tipo de contrato es obligatorio")
    @Pattern(regexp = "ARRIENDO|VENTA",
            message = "El tipo debe ser ARRIENDO o VENTA")
    private String tipoContrato;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @Size(max = 500)
    private String observaciones;
}