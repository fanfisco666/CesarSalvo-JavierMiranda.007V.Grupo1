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

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/Agente")
public class AgenteControllers {

    private final AgenteService agenteService;

    public AgenteControllers(AgenteService agenteService) {
        this.agenteService = agenteService;
    }

    @GetMapping
    public List<AgenteResponse> listar() {
        return agenteService.obtenerTodos();
    }

    @PostMapping
    public ResponseEntity<AgenteResponse> guardar(@Valid @RequestBody AgenteRequest request) {
        AgenteResponse response = agenteService.guardar(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgenteResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AgenteRequest request) {
        return ResponseEntity.ok(agenteService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        agenteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
