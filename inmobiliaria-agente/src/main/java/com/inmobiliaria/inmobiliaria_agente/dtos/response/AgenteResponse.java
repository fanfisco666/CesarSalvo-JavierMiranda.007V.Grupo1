package com.inmobiliaria.inmobiliaria_agente.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgenteResponse {

    private Long idAgente;
    private String rutAgente;
    private String correoAgente;
    private String nombreAgente;
    private String apellidosAgente;

}
