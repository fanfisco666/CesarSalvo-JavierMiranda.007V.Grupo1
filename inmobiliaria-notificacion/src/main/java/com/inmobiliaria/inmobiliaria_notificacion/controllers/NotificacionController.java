package com.inmobiliaria.inmobiliaria_notificacion.controllers;

import com.inmobiliaria.inmobiliaria_notificacion.dtos.request.NotificacionRequest;
import com.inmobiliaria.inmobiliaria_notificacion.dtos.response.NotificacionResponse;
import com.inmobiliaria.inmobiliaria_notificacion.services.NotificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Notificaciones", description = "Operaciones relacionadas con notificaciones en el sistema inmobiliario")
@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @Operation(summary = "Listar notificaciones", description = "Obtiene una lista de todas las notificaciones registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de notificaciones obtenida exitosamente")
    @GetMapping
    public List<NotificacionResponse> listar() {
        return notificacionService.obtenerTodos();
    }

    @Operation(summary = "Obtener notificación por ID", description = "Obtiene los detalles de una notificación específica por su ID")
    @ApiResponse(responseCode = "200", description = "Notificación obtenida exitosamente")
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.obtenerPorId(id));
    }

    @Operation(summary = "Obtener notificaciones por cliente", description = "Obtiene una lista de notificaciones asociadas a un cliente específico")
    @ApiResponse(responseCode = "200", description = "Notificaciones obtenidas exitosamente")
    @GetMapping("/cliente/{idCliente}")
    public List<NotificacionResponse> obtenerPorCliente(@PathVariable Long idCliente) {
        return notificacionService.obtenerPorCliente(idCliente);
    }

    @Operation(summary = "Obtener notificaciones no leídas por cliente", description = "Obtiene una lista de notificaciones no leídas asociadas a un cliente específico")
    @ApiResponse(responseCode = "200", description = "Notificaciones no leídas obtenidas exitosamente")
    @GetMapping("/cliente/{idCliente}/no-leidas")
    public List<NotificacionResponse> obtenerNoLeidas(@PathVariable Long idCliente) {
        return notificacionService.obtenerNoLeidasPorCliente(idCliente);
    }

    @Operation(summary = "Contar notificaciones no leídas por cliente", description = "Obtiene el número de notificaciones no leídas asociadas a un cliente específico")
    @ApiResponse(responseCode = "200", description = "Número de notificaciones no leídas obtenido exitosamente")
    @GetMapping("/cliente/{idCliente}/contar-no-leidas")
    public ResponseEntity<Long> contarNoLeidas(@PathVariable Long idCliente) {
        return ResponseEntity.ok(notificacionService.contarNoLeidas(idCliente));
    }

    @Operation(summary = "Crear notificación", description = "Crea una nueva notificación en el sistema")
    @ApiResponse(responseCode = "201", description = "Notificación creada exitosamente")
    @PostMapping
    public ResponseEntity<NotificacionResponse> guardar(
            @Valid @RequestBody NotificacionRequest request) {
        return ResponseEntity.ok(notificacionService.guardar(request));
    }

    @Operation(summary = "Marcar notificación como leída", description = "Marca una notificación específica como leída por su ID")
    @ApiResponse(responseCode = "200", description = "Notificación marcada como leída exitosamente")
    @PatchMapping("/{id}/leer")
    public ResponseEntity<NotificacionResponse> marcarComoLeida(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.marcarComoLeida(id));
    }

    @Operation(summary = "Eliminar notificación", description = "Elimina una notificación del sistema por su ID")
    @ApiResponse(responseCode = "204", description = "Notificación eliminada exitosamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}