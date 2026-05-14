package com.inmobiliaria.inmobiliaria_contrato.controllers;

import com.inmobiliaria.inmobiliaria_contrato.dtos.request.ContratoRequest;
import com.inmobiliaria.inmobiliaria_contrato.dtos.response.ContratoResponse;
import com.inmobiliaria.inmobiliaria_contrato.services.ContratoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/contratos")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping
    public List<ContratoResponse> listar() {
        return contratoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contratoService.obtenerPorId(id));
    }

    @GetMapping("/cliente/{idCliente}")
    public List<ContratoResponse> obtenerPorCliente(@PathVariable Long idCliente) {
        return contratoService.obtenerPorCliente(idCliente);
    }

    @GetMapping("/estado/{estado}")
    public List<ContratoResponse> obtenerPorEstado(@PathVariable String estado) {
        return contratoService.obtenerPorEstado(estado);
    }

    @PostMapping
    public ResponseEntity<ContratoResponse> guardar(@Valid @RequestBody ContratoRequest request) {
        return ResponseEntity.ok(contratoService.guardar(request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ContratoResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        return ResponseEntity.ok(contratoService.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        contratoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}