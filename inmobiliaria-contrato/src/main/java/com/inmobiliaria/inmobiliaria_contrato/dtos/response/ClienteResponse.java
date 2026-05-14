package com.inmobiliaria.inmobiliaria_contrato.dtos.response;

import lombok.Data;

@Data
public class ClienteResponse {
    private Long idCliente;
    private String rutCliente;
    private String nombreCliente;
    private String apellidosCliente;
    private String correoCliente;
}