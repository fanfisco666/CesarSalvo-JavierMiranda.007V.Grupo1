package com.inmobiliaria.inmobiliaria_mantencion.exceptions;

public class MantencionNotFoundException extends RuntimeException {

    public MantencionNotFoundException(Long id) {
        super("Mantención no encontrada con ID: " + id);
    }

    public MantencionNotFoundException(String mensaje) {
        super(mensaje);
    }

}
