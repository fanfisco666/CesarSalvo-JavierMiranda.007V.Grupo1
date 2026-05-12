package com.inmobiliaria.inmobiliaria_cliente.dtos.request;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClienteRequest {
    @NotBlank(message = "El RUT es obligatorio")
    @Size(max = 13)
    private String rutCliente;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombreCliente;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 150)
    private String apellidosCliente;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo inválido")
    @Size(max = 150)
    private String correoCliente;
}
