package com.inmobiliaria.inmobiliaria_propiedad.controllers;

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

import com.inmobiliaria.inmobiliaria_propiedad.dtos.request.PropiedadRequest;
import com.inmobiliaria.inmobiliaria_propiedad.dtos.response.PropiedadResponse;
import com.inmobiliaria.inmobiliaria_propiedad.services.PropiedadService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/Propiedad")
public class PropiedadControllers {

    private final PropiedadService propiedadService;

    public PropiedadControllers(PropiedadService propiedadService) {
        this.propiedadService = propiedadService;
    }

    @GetMapping
    public List<PropiedadResponse> listar() {
        return propiedadService.obtenerTodos();
    }

    @PostMapping
    public ResponseEntity<PropiedadResponse> guardar(@Valid @RequestBody PropiedadRequest request) {
        PropiedadResponse response = propiedadService.guardar(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropiedadResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PropiedadRequest request) {
        return ResponseEntity.ok(propiedadService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        propiedadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
