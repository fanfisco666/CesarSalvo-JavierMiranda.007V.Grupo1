package com.inmobiliaria.inmobiliaria_reporte.dtos.response;

import lombok.Data;

@Data
public class ClienteResponse {

    private Long idCliente;
    private String nombreCliente;
    private String apellidosCliente;
    private String correoCliente;

}
