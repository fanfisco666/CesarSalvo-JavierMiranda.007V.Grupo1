package com.inmobiliaria.inmobiliaria_visita.controllers;

import com.inmobiliaria.inmobiliaria_visita.dtos.request.VisitaRequest;
import com.inmobiliaria.inmobiliaria_visita.dtos.response.VisitaResponse;
import com.inmobiliaria.inmobiliaria_visita.services.VisitaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/visitas")
public class VisitaController {

    private final VisitaService visitaService;

    public VisitaController(VisitaService visitaService) {
        this.visitaService = visitaService;
    }

    @GetMapping
    public List<VisitaResponse> listar() {
        return visitaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(visitaService.obtenerPorId(id));
    }

    @GetMapping("/cliente/{idCliente}")
    public List<VisitaResponse> obtenerPorCliente(@PathVariable Long idCliente) {
        return visitaService.obtenerPorCliente(idCliente);
    }

    @GetMapping("/propiedad/{idPropiedad}")
    public List<VisitaResponse> obtenerPorPropiedad(@PathVariable Long idPropiedad) {
        return visitaService.obtenerPorPropiedad(idPropiedad);
    }

    @GetMapping("/estado/{estado}")
    public List<VisitaResponse> obtenerPorEstado(@PathVariable String estado) {
        return visitaService.obtenerPorEstado(estado);
    }

    @PostMapping
    public ResponseEntity<VisitaResponse> guardar(@Valid @RequestBody VisitaRequest request) {
        return ResponseEntity.ok(visitaService.guardar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VisitaRequest request) {
        return ResponseEntity.ok(visitaService.actualizar(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<VisitaResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        return ResponseEntity.ok(visitaService.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        visitaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}