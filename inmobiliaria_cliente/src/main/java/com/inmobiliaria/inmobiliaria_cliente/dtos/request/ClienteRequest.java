package com.inmobiliaria.inmobiliaria_cliente.dtos.request;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClienteRequest {
    @NotBlank(message = "El rut es obligatorio")
    private String rutCliente;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombreCliente;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellidosCliente;

    @NotNull(message = "El id del cliente es obligatorio")
    @Positive(message = "El id del cliente debe ser mayor a 0")
    private Long idCliente;

    @NotBlank(message = "El correo es obligatorio")
    private String correoCliente;
}
