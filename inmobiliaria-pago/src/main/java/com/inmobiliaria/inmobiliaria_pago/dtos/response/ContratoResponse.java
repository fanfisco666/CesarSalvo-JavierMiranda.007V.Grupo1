package com.inmobiliaria.inmobiliaria_pago.dtos.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la respuesta de un contrato desde el servicio de pago")
@Data
public class ContratoResponse {
    @Schema(description = "ID del contrato")
    private Long idContrato;
    @Schema(description = "ID del cliente")
    private Long idCliente;
    @Schema(description = "ID de la propiedad")
    private Long idPropiedad;
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
}