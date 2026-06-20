package com.inmobiliaria.inmobiliaria_pago.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO para la respuesta de un cliente desde el servicio de pago")
@Data
public class ClienteResponse {
    @Schema(description = "ID del cliente")
    private Long idCliente;
    @Schema(description = "RUT del cliente")
    private String rutCliente;
    @Schema(description = "Nombre del cliente")
    private String nombreCliente;
    @Schema(description = "Apellidos del cliente")
    private String apellidosCliente;
    @Schema(description = "Correo electrónico del cliente")
    private String correoCliente;
}