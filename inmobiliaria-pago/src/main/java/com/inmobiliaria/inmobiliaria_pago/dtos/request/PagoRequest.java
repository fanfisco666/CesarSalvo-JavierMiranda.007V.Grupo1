package com.inmobiliaria.inmobiliaria_pago.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PagoRequest {

    @NotNull(message = "El ID del contrato es obligatorio")
    private Long idContrato;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idCliente;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "1.0", message = "El monto debe ser mayor a 0")
    private BigDecimal montoPago;

    @NotNull(message = "La fecha de pago es obligatoria")
    private LocalDate fechaPago;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate fechaVencimiento;

    @NotBlank(message = "El método de pago es obligatorio")
    @Pattern(regexp = "TRANSFERENCIA|CHEQUE|EFECTIVO|TARJETA",
            message = "Método debe ser TRANSFERENCIA, CHEQUE, EFECTIVO o TARJETA")
    private String metodoPago;

    private Integer numeroCuota;

    @Size(max = 500)
    private String observaciones;
}