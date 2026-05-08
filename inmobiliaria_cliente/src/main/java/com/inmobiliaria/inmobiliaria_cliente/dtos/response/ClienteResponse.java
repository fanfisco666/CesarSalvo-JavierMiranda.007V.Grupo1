package com.inmobiliaria.inmobiliaria_cliente.dtos.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteResponse {
    private Long idCliente;
    private String rutCliente;
    private String nombreCliente;
    private String apellidosCliente;
    private String correoCliente;
}
