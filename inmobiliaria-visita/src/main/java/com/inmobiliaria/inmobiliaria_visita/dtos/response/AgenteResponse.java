package com.inmobiliaria.inmobiliaria_visita.dtos.response;

import lombok.Data;

@Data
public class AgenteResponse {
    private Long idAgente;
    private String rutAgente;
    private String nombreAgente;
    private String apellidosAgente;
    private String correoAgente;
}