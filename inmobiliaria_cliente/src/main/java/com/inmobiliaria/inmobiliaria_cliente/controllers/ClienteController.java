package com.inmobiliaria.inmobiliaria_cliente.controllers;

import com.inmobiliaria.inmobiliaria_cliente.models.ClienteModel;
import com.inmobiliaria.inmobiliaria_cliente.services.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteModel> listar() {
        return clienteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> obtenerPorId(@PathVariable Long id) {
        Optional<ClienteModel> cliente= clienteService.obtenerPorId(id);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<ClienteModel> obtenerPorRut(@PathVariable String rut) {
        Optional<ClienteModel> cliente = clienteService.obtenerPorRut(rut);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteModel> guardar(@RequestBody ClienteModel cliente) {
        return ResponseEntity.ok(clienteService.guardar(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

