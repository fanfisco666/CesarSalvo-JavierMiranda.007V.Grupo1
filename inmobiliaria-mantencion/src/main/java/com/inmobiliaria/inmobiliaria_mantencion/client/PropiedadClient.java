package com.inmobiliaria.inmobiliaria_mantencion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.inmobiliaria.inmobiliaria_mantencion.dtos.external.PropiedadResponse;

@FeignClient(name = "propiedad-service", url = "http://localhost:8082/api/v1/propiedad")
public interface PropiedadClient {

    @GetMapping("/{id}")
    PropiedadResponse obtenerPropiedadPorId(@PathVariable Long id);

}
