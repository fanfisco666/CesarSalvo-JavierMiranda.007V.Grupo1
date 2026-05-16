package com.inmobiliaria.inmobiliaria_pago.dtos.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class PagoResponse {
    private Long idPago;
    private Long idContrato;
    private Long idCliente;
    private BigDecimal montoPago;
    private LocalDate fechaPago;
    private LocalDate fechaVencimiento;
    private String metodoPago;
    private String estado;
    private Integer numeroCuota;
    private String observaciones;
    private Boolean enMora;
}