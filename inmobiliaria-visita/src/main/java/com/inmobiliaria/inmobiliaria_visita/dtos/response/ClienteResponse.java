package com.inmobiliaria.inmobiliaria_visita.dtos.response;

import lombok.Data;

@Data
public class ClienteResponse {
    private Long idCliente;
    private String rutCliente;
    private String nombreCliente;
    private String apellidosCliente;
    private String correoCliente;
}