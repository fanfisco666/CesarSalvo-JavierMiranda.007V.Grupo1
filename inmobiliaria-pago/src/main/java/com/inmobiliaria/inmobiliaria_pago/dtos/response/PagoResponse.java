package com.inmobiliaria.inmobiliaria_pago.dtos.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la respuesta de un pago desde el servicio de pago")
@Data
@Builder
public class PagoResponse {
    @Schema(description = "ID del pago")
    private Long idPago;
    @Schema(description = "ID del contrato")
    private Long idContrato;
    @Schema(description = "ID del cliente")
    private Long idCliente;
    @Schema(description = "Monto del pago")
    private BigDecimal montoPago;
    @Schema(description = "Fecha del pago")
    private LocalDate fechaPago;
    @Schema(description = "Fecha de vencimiento")
    private LocalDate fechaVencimiento;
    @Schema(description = "Método de pago")
    private String metodoPago;
    @Schema(description = "Estado del pago")
    private String estado;
    @Schema(description = "Número de cuota")
    private Integer numeroCuota;
    @Schema(description = "Observaciones")
    private String observaciones;
    @Schema(description = "Indica si el pago está en mora")
    private Boolean enMora;
}