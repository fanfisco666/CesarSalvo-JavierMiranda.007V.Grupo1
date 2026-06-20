package com.inmobiliaria.inmobiliaria_pago.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la creación de un pago")
@Data
public class PagoRequest {

    @NotNull(message = "El ID del contrato es obligatorio")
    @Schema(description = "ID del contrato")
    private Long idContrato;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Schema(description = "ID del cliente")
    private Long idCliente;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "1.0", message = "El monto debe ser mayor a 0")
    @Schema(description = "Monto del pago")
    private BigDecimal montoPago;

    @NotNull(message = "La fecha de pago es obligatoria")
    @Schema(description = "Fecha de pago")
    private LocalDate fechaPago;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Schema(description = "Fecha de vencimiento")
    private LocalDate fechaVencimiento;

    @NotBlank(message = "El método de pago es obligatorio")
    @Pattern(regexp = "TRANSFERENCIA|CHEQUE|EFECTIVO|TARJETA", message = "Método debe ser TRANSFERENCIA, CHEQUE, EFECTIVO o TARJETA")
    @Schema(description = "Método de pago")
    private String metodoPago;

    @Schema(description = "Número de cuota")
    private Integer numeroCuota;

    @Size(max = 500)
    @Schema(description = "Observaciones")
    private String observaciones;
}