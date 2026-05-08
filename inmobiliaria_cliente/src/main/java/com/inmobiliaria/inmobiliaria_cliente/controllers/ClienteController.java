package com.inmobiliaria.inmobiliaria_cliente.controllers;

import com.inmobiliaria.inmobiliaria_cliente.dtos.request.ClienteRequest;
import com.inmobiliaria.inmobiliaria_cliente.dtos.response.ClienteResponse;
import com.inmobiliaria.inmobiliaria_cliente.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        return clienteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<ClienteResponse> obtenerPorRut(@PathVariable String rut) {
        return ResponseEntity.ok(clienteService.obtenerPorRut(rut));
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> guardar(@Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = clienteService.guardar(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

