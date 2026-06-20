package com.inmobiliaria.inmobiliaria_propiedad.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inmobiliaria.inmobiliaria_propiedad.dtos.request.PropiedadRequest;
import com.inmobiliaria.inmobiliaria_propiedad.dtos.response.PropiedadResponse;
import com.inmobiliaria.inmobiliaria_propiedad.services.PropiedadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Propiedades", description = "Operaciones relacionadas con propiedades en el sistema inmobiliario")
@Slf4j
@RestController
@RequestMapping("/api/v1/propiedad")
public class PropiedadControllers {

    private final PropiedadService propiedadService;

    public PropiedadControllers(PropiedadService propiedadService) {
        this.propiedadService = propiedadService;
    }

    @Operation(summary = "Listar propiedades", description = "Obtiene una lista de todas las propiedades registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de propiedades obtenida exitosamente")
    @GetMapping
    public List<PropiedadResponse> listar() {
        log.info("Listando todas las propiedades");
        return propiedadService.obtenerTodos();
    }

    @Operation(summary = "Obtener propiedad por ID", description = "Obtiene los detalles de una propiedad específica por su ID")
    @ApiResponse(responseCode = "200", description = "Propiedad obtenida exitosamente")
    @ApiResponse(responseCode = "404", description = "Propiedad no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<PropiedadResponse> obtenerPorId(
            @PathVariable Long id) {
        log.info("Buscando propiedad con ID: {}", id);
        return ResponseEntity.ok(propiedadService.obtenerPorId(id));
    }

    @Operation(summary = "Crear propiedad", description = "Crea una nueva propiedad en el sistema")
    @ApiResponse(responseCode = "201", description = "Propiedad creada exitosamente")
    @PostMapping
    public ResponseEntity<PropiedadResponse> guardar(@Valid @RequestBody PropiedadRequest request) {
        log.info("Guardando nueva propiedad");
        PropiedadResponse response = propiedadService.guardar(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar propiedad", description = "Actualiza los detalles de una propiedad existente por su ID")
    @ApiResponse(responseCode = "200", description = "Propiedad actualizada exitosamente")
    @ApiResponse(responseCode = "404", description = "Propiedad no encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<PropiedadResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PropiedadRequest request) {
        log.info("Actualizando propiedad con ID: {}", id);
        return ResponseEntity.ok(propiedadService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar propiedad", description = "Elimina una propiedad específica por su ID")
    @ApiResponse(responseCode = "204", description = "Propiedad eliminada exitosamente")
    @ApiResponse(responseCode = "404", description = "Propiedad no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Eliminando propiedad con ID: {}", id);
        propiedadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
