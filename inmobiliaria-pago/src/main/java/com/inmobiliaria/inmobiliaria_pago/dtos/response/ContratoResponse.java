package com.inmobiliaria.inmobiliaria_pago.dtos.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ContratoResponse {
    private Long idContrato;
    private Long idCliente;
    private Long idPropiedad;
    private String tipoContrato;
    private BigDecimal montoTotal;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
}