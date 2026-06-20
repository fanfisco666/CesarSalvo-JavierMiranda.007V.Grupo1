package com.inmobiliaria.inmobiliaria_cliente.controllers;

import com.inmobiliaria.inmobiliaria_cliente.dtos.request.ClienteRequest;
import com.inmobiliaria.inmobiliaria_cliente.dtos.response.ClienteResponse;
import com.inmobiliaria.inmobiliaria_cliente.services.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Clientes", description = "Operaciones relacionadas con clientes en el sistema inmobiliario")
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Listar clientes", description = "Obtiene una lista de todos los clientes registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente")
    @GetMapping
    public List<ClienteResponse> listar() {
        return clienteService.obtenerTodos();
    }

    @Operation(summary = "Obtener cliente por ID", description = "Obtiene los detalles de un cliente específico por su ID")
    @ApiResponse(responseCode = "200", description = "Cliente obtenido exitosamente")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @Operation(summary = "Obtener cliente por RUT", description = "Obtiene los detalles de un cliente específico por su RUT")
    @ApiResponse(responseCode = "200", description = "Cliente obtenido exitosamente")
    @GetMapping("/rut/{rut}")
    public ResponseEntity<ClienteResponse> obtenerPorRut(@PathVariable String rut) {
        return ResponseEntity.ok(clienteService.obtenerPorRut(rut));
    }

    @Operation(summary = "Crear cliente", description = "Crea un nuevo cliente en el sistema")
    @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente")
    @PostMapping
    public ResponseEntity<ClienteResponse> guardar(@Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = clienteService.guardar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Actualizar cliente", description = "Actualiza los detalles de un cliente existente por su ID")
    @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(clienteService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente del sistema por su ID")
    @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
