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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/mantencion")
public class MantencionController {

    private final MantencionService mantencionService;

    public MantencionController(MantencionService mantencionService) {
        this.mantencionService = mantencionService;
    }

    @PostMapping
    public ResponseEntity<MantencionResponse> crear(@Valid @RequestBody MantencionRequest request) {
        MantencionResponse response = mantencionService.crearMantencion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MantencionResponse>> obtenerTodas() {
        return ResponseEntity.ok(mantencionService.obtenerTodasLasMantenciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MantencionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mantencionService.obtenerMantencionPorId(id));
    }

    @GetMapping("/estado")
    public ResponseEntity<List<MantencionResponse>> obtenerPorEstado(@RequestParam String estado) {
        return ResponseEntity.ok(mantencionService.obtenerMantencionesPorEstado(estado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MantencionResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MantencionRequest request) {
        return ResponseEntity.ok(mantencionService.actualizarMantencion(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mantencionService.eliminarMantencion(id);
        return ResponseEntity.noContent().build();
    }
}
