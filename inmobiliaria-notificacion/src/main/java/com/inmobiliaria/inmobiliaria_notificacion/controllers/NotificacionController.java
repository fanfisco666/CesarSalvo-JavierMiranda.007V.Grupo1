package com.inmobiliaria.inmobiliaria_notificacion.controllers;

import com.inmobiliaria.inmobiliaria_notificacion.dtos.request.NotificacionRequest;
import com.inmobiliaria.inmobiliaria_notificacion.dtos.response.NotificacionResponse;
import com.inmobiliaria.inmobiliaria_notificacion.services.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping
    public List<NotificacionResponse> listar() {
        return notificacionService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.obtenerPorId(id));
    }

    @GetMapping("/cliente/{idCliente}")
    public List<NotificacionResponse> obtenerPorCliente(@PathVariable Long idCliente) {
        return notificacionService.obtenerPorCliente(idCliente);
    }

    @GetMapping("/cliente/{idCliente}/no-leidas")
    public List<NotificacionResponse> obtenerNoLeidas(@PathVariable Long idCliente) {
        return notificacionService.obtenerNoLeidasPorCliente(idCliente);
    }

    @GetMapping("/cliente/{idCliente}/contar-no-leidas")
    public ResponseEntity<Long> contarNoLeidas(@PathVariable Long idCliente) {
        return ResponseEntity.ok(notificacionService.contarNoLeidas(idCliente));
    }

    @PostMapping
    public ResponseEntity<NotificacionResponse> guardar(
            @Valid @RequestBody NotificacionRequest request) {
        return ResponseEntity.ok(notificacionService.guardar(request));
    }

    @PatchMapping("/{id}/leer")
    public ResponseEntity<NotificacionResponse> marcarComoLeida(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.marcarComoLeida(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}