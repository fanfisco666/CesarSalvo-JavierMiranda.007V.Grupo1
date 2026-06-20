package com.inmobiliaria.inmobiliaria_agente.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "DTO para la creación o actualización de un agente inmobiliario")
@Data
public class AgenteRequest {
    @Schema(description = "Nombre del agente")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombreAgente;

    @Schema(description = "Apellidos del agente")
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 150)
    private String apellidosAgente;

    @Schema(description = "Correo electrónico del agente")
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo inválido")
    @Size(max = 100)
    private String correoAgente;

    @Schema(description = "RUT del agente")
    @NotBlank(message = "El RUT es obligatorio")
    @Size(max = 12, message = "El RUT no puede exceder los 12 caracteres")
    private String rutAgente;

}
