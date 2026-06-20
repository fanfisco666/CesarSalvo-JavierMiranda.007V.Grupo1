package com.inmobiliaria.inmobiliaria_contrato.controllers;

import com.inmobiliaria.inmobiliaria_contrato.dtos.request.ContratoRequest;
import com.inmobiliaria.inmobiliaria_contrato.dtos.response.ContratoResponse;
import com.inmobiliaria.inmobiliaria_contrato.services.ContratoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Contratos", description = "Operaciones relacionadas con contratos en el sistema inmobiliario")
@RestController
@RequestMapping("/api/v1/contratos")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @Operation(summary = "Listar contratos", description = "Obtiene una lista de todos los contratos registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de contratos obtenida exitosamente")
    @GetMapping
    public List<ContratoResponse> listar() {
        return contratoService.obtenerTodos();
    }

    @Operation(summary = "Obtener contrato por ID", description = "Obtiene los detalles de un contrato específico por su ID")
    @ApiResponse(responseCode = "200", description = "Contrato obtenido exitosamente")
    @GetMapping("/{id}")
    public ResponseEntity<ContratoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contratoService.obtenerPorId(id));
    }

    @Operation(summary = "Obtener contrato por cliente", description = "Obtiene una lista de contratos asociados a un cliente específico")
    @ApiResponse(responseCode = "200", description = "Contratos obtenidos exitosamente")
    @GetMapping("/cliente/{idCliente}")
    public List<ContratoResponse> obtenerPorCliente(@PathVariable Long idCliente) {
        return contratoService.obtenerPorCliente(idCliente);
    }

    @Operation(summary = "Obtener contrato por estado", description = "Obtiene una lista de contratos con un estado específico")
    @ApiResponse(responseCode = "200", description = "Contratos obtenidos exitosamente")
    @GetMapping("/estado/{estado}")
    public List<ContratoResponse> obtenerPorEstado(@PathVariable String estado) {
        return contratoService.obtenerPorEstado(estado);
    }

    @Operation(summary = "Crear contrato", description = "Crea un nuevo contrato en el sistema")
    @ApiResponse(responseCode = "201", description = "Contrato creado exitosamente")
    @PostMapping
    public ResponseEntity<ContratoResponse> guardar(@Valid @RequestBody ContratoRequest request) {
        return ResponseEntity.ok(contratoService.guardar(request));
    }

    @Operation(summary = "Cambiar estado de contrato", description = "Actualiza el estado de un contrato específico por su ID")
    @ApiResponse(responseCode = "200", description = "Estado del contrato actualizado exitosamente")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ContratoResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        return ResponseEntity.ok(contratoService.cambiarEstado(id, estado));
    }

    @Operation(summary = "Eliminar contrato", description = "Elimina un contrato del sistema por su ID")
    @ApiResponse(responseCode = "204", description = "Contrato eliminado exitosamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        contratoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}