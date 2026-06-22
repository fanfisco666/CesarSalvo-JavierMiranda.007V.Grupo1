package com.inmobiliaria.inmobiliaria_reporte.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.inmobiliaria.inmobiliaria_reporte.dtos.request.ReporteRequest;
import com.inmobiliaria.inmobiliaria_reporte.dtos.response.ReporteResponse;
import com.inmobiliaria.inmobiliaria_reporte.services.ReporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Reportes", description = "Operaciones relacionadas con reportes en el sistema inmobiliario")
@Slf4j
@RestController
@RequestMapping("/api/v1/reporte")

public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @Operation(summary = "Listar reportes", description = "Obtiene una lista de todos los reportes registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de reportes obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<ReporteResponse>> obtenerReportes() {
        return ResponseEntity.ok(reporteService.obtenerReportes());
    }

    @Operation(summary = "Obtener reporte por ID", description = "Obtiene los detalles de un reporte específico por su ID")
    @ApiResponse(responseCode = "200", description = "Reporte obtenido exitosamente")
    @PostMapping
    public ResponseEntity<ReporteResponse> guardar(@Valid @RequestBody ReporteRequest request) {
        log.info("Guardando nuevo reporte");
        ReporteResponse response = reporteService.guardar(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar reporte", description = "Actualiza los detalles de un reporte existente por su ID")
    @ApiResponse(responseCode = "200", description = "Reporte actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<ReporteResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ReporteRequest request) {
        log.info("Actualizando reporte con ID: {}", id);
        return ResponseEntity.ok(reporteService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar reporte", description = "Elimina un reporte específico por su ID")
    @ApiResponse(responseCode = "204", description = "Reporte eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Eliminando reporte con ID: {}", id);
        reporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener reporte por ID", description = "Obtiene los detalles de un reporte específico por su ID")
    @ApiResponse(responseCode = "200", description = "Reporte obtenido exitosamente")
    @GetMapping("/{id}")
    public ResponseEntity<ReporteResponse> obtenerPorId(@PathVariable Long id) {
        log.info("Obteniendo reporte con ID: {}", id);
        return ResponseEntity.ok(reporteService.obtenerPorId(id));
    }

}
