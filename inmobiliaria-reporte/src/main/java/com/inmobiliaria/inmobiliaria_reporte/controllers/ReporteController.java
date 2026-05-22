package com.inmobiliaria.inmobiliaria_reporte.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.inmobiliaria.inmobiliaria_reporte.dtos.request.ReporteRequest;
import com.inmobiliaria.inmobiliaria_reporte.dtos.response.ReporteResponse;
import com.inmobiliaria.inmobiliaria_reporte.services.ReporteService;

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

@Slf4j
@RestController
@RequestMapping("api/v1/reporte")

public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public ResponseEntity<List<ReporteResponse>> obtenerReportes() {
        return ResponseEntity.ok(reporteService.obtenerReportes());
    }

    @PostMapping
    public ResponseEntity<ReporteResponse> guardar(@Valid @RequestBody ReporteRequest request) {
        log.info("Guardando nuevo reporte");
        ReporteResponse response = reporteService.guardar(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ReporteRequest request) {
        log.info("Actualizando reporte con ID: {}", id);
        return ResponseEntity.ok(reporteService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Eliminando reporte con ID: {}", id);
        reporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteResponse> obtenerPorId(@PathVariable Long id) {
        log.info("Obteniendo reporte con ID: {}", id);
        return ResponseEntity.ok(reporteService.obtenerPorId(id));
    }

}
