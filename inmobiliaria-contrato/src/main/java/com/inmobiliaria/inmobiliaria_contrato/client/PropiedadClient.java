package com.inmobiliaria.inmobiliaria_contrato.client;

import com.inmobiliaria.inmobiliaria_contrato.dtos.response.PropiedadResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inmobiliaria-propiedad", url = "http://localhost:8082")
public interface PropiedadClient {

    @GetMapping("/api/v1/propiedades/{id}")
    PropiedadResponse obtenerPropiedadPorId(@PathVariable("id") Long id);
}