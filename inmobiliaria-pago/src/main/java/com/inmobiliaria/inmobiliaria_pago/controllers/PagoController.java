package com.inmobiliaria.inmobiliaria_pago.controllers;

import com.inmobiliaria.inmobiliaria_pago.dtos.request.PagoRequest;
import com.inmobiliaria.inmobiliaria_pago.dtos.response.PagoResponse;
import com.inmobiliaria.inmobiliaria_pago.services.PagoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public List<PagoResponse> listar() {
        return pagoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @GetMapping("/contrato/{idContrato}")
    public List<PagoResponse> obtenerPorContrato(@PathVariable Long idContrato) {
        return pagoService.obtenerPorContrato(idContrato);
    }

    @GetMapping("/cliente/{idCliente}")
    public List<PagoResponse> obtenerPorCliente(@PathVariable Long idCliente) {
        return pagoService.obtenerPorCliente(idCliente);
    }

    @GetMapping("/mora")
    public List<PagoResponse> obtenerEnMora() {
        return pagoService.obtenerEnMora();
    }

    @PostMapping
    public ResponseEntity<PagoResponse> guardar(@Valid @RequestBody PagoRequest request) {
        return ResponseEntity.ok(pagoService.guardar(request));
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<PagoResponse> confirmarPago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.confirmarPago(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}