package com.inmobiliaria.inmobiliaria_visita.dtos.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class VisitaResponse {
    private Long idVisita;
    private Long idCliente;
    private Long idPropiedad;
    private Long idAgente;
    private LocalDate fechaVisita;
    private LocalTime horaVisita;
    private String estado;
    private String observaciones;
}