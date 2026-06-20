package com.inmobiliaria.inmobiliaria_reporte.dtos.response;

import lombok.Data;

@Schema(description = "DTO para la respuesta de un cliente")
@Data
public class ClienteResponse {
    @Schema(description = "ID del cliente")
    private Long idCliente;
    @Schema(description = "Nombre del cliente")
    private String nombreCliente;
    @Schema(description = "Apellidos del cliente")
    private String apellidosCliente;
    @Schema(description = "Correo del cliente")
    private String correoCliente;

}
