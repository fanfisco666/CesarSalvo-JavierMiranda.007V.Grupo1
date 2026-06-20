package com.inmobiliaria.inmobiliaria_cliente.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Schema(description = "DTO para la creación o actualización de un cliente")
@Data
public class ClienteRequest {
    @Schema(description = "RUT del cliente")
    @NotBlank(message = "El RUT es obligatorio")
    @Size(max = 13)
    private String rutCliente;

    @Schema(description = "Nombre del cliente")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombreCliente;

    @Schema(description = "Apellidos del cliente")
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 150)
    private String apellidosCliente;

    @Schema(description = "Correo electrónico del cliente")
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo inválido")
    @Size(max = 150)
    private String correoCliente;
}
