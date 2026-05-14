package com.inmobiliaria.inmobiliaria_contrato.dtos.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class VisitaResponse {
    private Long idVisita;
    private Long idCliente;
    private Long idPropiedad;
    private Long idAgente;
    private LocalDate fechaVisita;
    private LocalTime horaVisita;
    private String estado;
}