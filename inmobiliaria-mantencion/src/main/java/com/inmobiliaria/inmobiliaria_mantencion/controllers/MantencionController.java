package com.inmobiliaria.inmobiliaria_mantencion.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inmobiliaria.inmobiliaria_mantencion.dtos.request.MantencionRequest;
import com.inmobiliaria.inmobiliaria_mantencion.dtos.response.MantencionResponse;
import com.inmobiliaria.inmobiliaria_mantencion.service.MantencionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Mantenciones", description = "Operaciones relacionadas con mantenciones en el sistema inmobiliario")
@RestController
@RequestMapping("/api/v1/mantencion")
public class MantencionController {

    private final MantencionService mantencionService;

    public MantencionController(MantencionService mantencionService) {
        this.mantencionService = mantencionService;
    }

    @Operation(summary = "Crear mantención", description = "Crea una nueva mantención en el sistema")
    @ApiResponse(responseCode = "201", description = "Mantención creada exitosamente")
    @PostMapping
    public ResponseEntity<MantencionResponse> crear(@Valid @RequestBody MantencionRequest request) {
        MantencionResponse response = mantencionService.crearMantencion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar mantenciones", description = "Obtiene una lista de todas las mantenciones registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de mantenciones obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<MantencionResponse>> obtenerTodas() {
        return ResponseEntity.ok(mantencionService.obtenerTodasLasMantenciones());
    }

    @Operation(summary = "Obtener mantención por ID", description = "Obtiene los detalles de una mantención específica por su ID")
    @ApiResponse(responseCode = "200", description = "Mantención obtenida exitosamente")
    @GetMapping("/{id}")
    public ResponseEntity<MantencionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mantencionService.obtenerMantencionPorId(id));
    }

    @Operation(summary = "Obtener mantenciones por estado", description = "Obtiene una lista de mantenciones con un estado específico")
    @ApiResponse(responseCode = "200", description = "Mantenciones obtenidas exitosamente")
    @GetMapping("/estado")
    public ResponseEntity<List<MantencionResponse>> obtenerPorEstado(@RequestParam String estado) {
        return ResponseEntity.ok(mantencionService.obtenerMantencionesPorEstado(estado));
    }

    @Operation(summary = "Actualizar mantención", description = "Actualiza los detalles de una mantención existente por su ID")
    @ApiResponse(responseCode = "200", description = "Mantención actualizada exitosamente")
    @ApiResponse(responseCode = "404", description = "Mantención no encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<MantencionResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MantencionRequest request) {
        return ResponseEntity.ok(mantencionService.actualizarMantencion(id, request));
    }

    @Operation(summary = "Eliminar mantención", description = "Elimina una mantención del sistema por su ID")
    @ApiResponse(responseCode = "204", description = "Mantención eliminada exitosamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mantencionService.eliminarMantencion(id);
        return ResponseEntity.noContent().build();
    }
}
