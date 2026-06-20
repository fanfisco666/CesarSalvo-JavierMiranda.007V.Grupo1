package com.inmobiliaria.inmobiliaria_visita.controllers;

import com.inmobiliaria.inmobiliaria_visita.dtos.request.VisitaRequest;
import com.inmobiliaria.inmobiliaria_visita.dtos.response.VisitaResponse;
import com.inmobiliaria.inmobiliaria_visita.services.VisitaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Visitas", description = "Operaciones relacionadas con visitas en el sistema inmobiliario")
@RestController
@RequestMapping("/api/v1/visitas")
public class VisitaController {

    private final VisitaService visitaService;

    public VisitaController(VisitaService visitaService) {
        this.visitaService = visitaService;
    }

    @Operation(summary = "Listar visitas", description = "Obtiene una lista de todas las visitas registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de visitas obtenida exitosamente")
    @GetMapping
    public List<VisitaResponse> listar() {
        return visitaService.obtenerTodos();
    }

    @Operation(summary = "Obtener visita por ID", description = "Obtiene los detalles de una visita específica por su ID")
    @ApiResponse(responseCode = "200", description = "Visita obtenida exitosamente")
    @ApiResponse(responseCode = "404", description = "Visita no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<VisitaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(visitaService.obtenerPorId(id));
    }

    @Operation(summary = "Obtener visitas por cliente", description = "Obtiene una lista de visitas asociadas a un cliente específico")
    @ApiResponse(responseCode = "200", description = "Visitas obtenidas exitosamente")
    @GetMapping("/cliente/{idCliente}")
    public List<VisitaResponse> obtenerPorCliente(@PathVariable Long idCliente) {
        return visitaService.obtenerPorCliente(idCliente);
    }

    @Operation(summary = "Obtener visitas por propiedad", description = "Obtiene una lista de visitas asociadas a una propiedad específica")
    @ApiResponse(responseCode = "200", description = "Visitas obtenidas exitosamente")
    @GetMapping("/propiedad/{idPropiedad}")
    public List<VisitaResponse> obtenerPorPropiedad(@PathVariable Long idPropiedad) {
        return visitaService.obtenerPorPropiedad(idPropiedad);
    }

    @Operation(summary = "Obtener visitas por estado", description = "Obtiene una lista de visitas con un estado específico")
    @ApiResponse(responseCode = "200", description = "Visitas obtenidas exitosamente")
    @GetMapping("/estado/{estado}")
    public List<VisitaResponse> obtenerPorEstado(@PathVariable String estado) {
        return visitaService.obtenerPorEstado(estado);
    }

    @Operation(summary = "Crear visita", description = "Crea una nueva visita en el sistema")
    @ApiResponse(responseCode = "201", description = "Visita creada exitosamente")
    @PostMapping
    public ResponseEntity<VisitaResponse> guardar(@Valid @RequestBody VisitaRequest request) {
        return ResponseEntity.ok(visitaService.guardar(request));
    }

    @Operation(summary = "Actualizar visita", description = "Actualiza los detalles de una visita existente en el sistema")
    @ApiResponse(responseCode = "200", description = "Visita actualizada exitosamente")
    @PutMapping("/{id}")
    public ResponseEntity<VisitaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VisitaRequest request) {
        return ResponseEntity.ok(visitaService.actualizar(id, request));
    }

    @Operation(summary = "Cambiar estado de visita", description = "Actualiza el estado de una visita específica por su ID")
    @ApiResponse(responseCode = "200", description = "Estado de la visita actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Visita no encontrada")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<VisitaResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        return ResponseEntity.ok(visitaService.cambiarEstado(id, estado));
    }

    @Operation(summary = "Eliminar visita", description = "Elimina una visita del sistema por su ID")
    @ApiResponse(responseCode = "204", description = "Visita eliminada exitosamente")
    @ApiResponse(responseCode = "404", description = "Visita no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        visitaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}