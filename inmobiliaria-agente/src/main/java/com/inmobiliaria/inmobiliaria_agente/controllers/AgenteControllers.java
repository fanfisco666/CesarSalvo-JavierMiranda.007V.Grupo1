package com.inmobiliaria.inmobiliaria_agente.controllers;

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

import com.inmobiliaria.inmobiliaria_agente.dtos.request.AgenteRequest;
import com.inmobiliaria.inmobiliaria_agente.dtos.response.AgenteResponse;
import com.inmobiliaria.inmobiliaria_agente.services.AgenteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Agentes", description = "Operaciones relacionadas con agentes en el sistema inmobiliario")
@Slf4j
@RestController
@RequestMapping("/api/v1/agente")
public class AgenteControllers {

    private final AgenteService agenteService;

    public AgenteControllers(AgenteService agenteService) {
        this.agenteService = agenteService;
    }

    @Operation(summary = "Listar agentes", description = "Obtiene una lista de todos los agentes registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de agentes obtenida exitosamente")
    @GetMapping
    public List<AgenteResponse> listar() {
        log.info("Listando todos los agentes");
        return agenteService.obtenerTodos();
    }

    @Operation(summary = "Obtener agente por ID", description = "Obtiene los detalles de un agente específico por su ID")
    @ApiResponse(responseCode = "200", description = "Agente obtenido exitosamente")
    @GetMapping("/{id}")
    public ResponseEntity<AgenteResponse> obtenerPorId(@PathVariable Long id) {
        log.info("Buscando agente con ID: {}", id);
        return ResponseEntity.ok(agenteService.obtenerPorId(id));
    }

    @Operation(summary = "Crear agente", description = "Crea un nuevo agente en el sistema")
    @ApiResponse(responseCode = "201", description = "Agente creado exitosamente")
    @PostMapping
    public ResponseEntity<AgenteResponse> guardar(@Valid @RequestBody AgenteRequest request) {
        log.info("Guardando nuevo agente");
        AgenteResponse response = agenteService.guardar(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar agente", description = "Actualiza los detalles de un agente existente por su ID")
    @ApiResponse(responseCode = "200", description = "Agente actualizado exitosamente")
    @PutMapping("/{id}")
    public ResponseEntity<AgenteResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AgenteRequest request) {
        log.info("Actualizando agente con ID: {}", id);
        return ResponseEntity.ok(agenteService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar agente", description = "Elimina un agente existente por su ID")
    @ApiResponse(responseCode = "204", description = "Agente eliminado exitosamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Eliminando agente con ID: {}", id);
        agenteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
