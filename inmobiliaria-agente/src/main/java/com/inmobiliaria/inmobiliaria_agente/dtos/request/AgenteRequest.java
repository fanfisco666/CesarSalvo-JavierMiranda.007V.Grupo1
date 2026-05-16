package com.inmobiliaria.inmobiliaria_agente.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AgenteRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombreAgente;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 150)
    private String apellidosAgente;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo inválido")
    @Size(max = 100)
    private String correoAgente;

    @NotBlank(message = "El RUT es obligatorio")
    @Size(max = 12, message = "El RUT no puede exceder los 12 caracteres")
    private String rutAgente;

}
