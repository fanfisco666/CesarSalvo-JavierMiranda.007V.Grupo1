package com.inmobiliaria.inmobiliaria_contrato.dtos.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la respuesta de un contrato de arriendo o venta")
@Data
@Builder
public class ContratoResponse {
    @Schema(description = "ID del contrato")
    private Long idContrato;
    @Schema(description = "ID del cliente")
    private Long idCliente;
    @Schema(description = "ID de la propiedad")
    private Long idPropiedad;
    @Schema(description = "ID del agente")
    private Long idAgente;
    @Schema(description = "ID de la visita")
    private Long idVisita;
    @Schema(description = "Tipo de contrato")
    private String tipoContrato;
    @Schema(description = "Monto total del contrato")
    private BigDecimal montoTotal;
    @Schema(description = "Fecha de inicio del contrato")
    private LocalDate fechaInicio;
    @Schema(description = "Fecha de fin del contrato")
    private LocalDate fechaFin;
    @Schema(description = "Estado del contrato")
    private String estado;
    @Schema(description = "Observaciones sobre el contrato")
    private String observaciones;
}