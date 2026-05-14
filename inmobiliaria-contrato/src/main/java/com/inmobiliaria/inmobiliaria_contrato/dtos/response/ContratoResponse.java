package com.inmobiliaria.inmobiliaria_contrato.dtos.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ContratoResponse {
    private Long idContrato;
    private Long idCliente;
    private Long idPropiedad;
    private Long idAgente;
    private Long idVisita;
    private String tipoContrato;
    private BigDecimal montoTotal;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private String observaciones;
}