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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/agente")
public class AgenteControllers {

    private final AgenteService agenteService;

    public AgenteControllers(AgenteService agenteService) {
        this.agenteService = agenteService;
    }

    @GetMapping
    public List<AgenteResponse> listar() {
        log.info("Listando todos los agentes");
        return agenteService.obtenerTodos();
    }

    @PostMapping
    public ResponseEntity<AgenteResponse> guardar(@Valid @RequestBody AgenteRequest request) {
        log.info("Guardando nuevo agente");
        AgenteResponse response = agenteService.guardar(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgenteResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AgenteRequest request) {
        log.info("Actualizando agente con ID: {}", id);
        return ResponseEntity.ok(agenteService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Eliminando agente con ID: {}", id);
        agenteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
