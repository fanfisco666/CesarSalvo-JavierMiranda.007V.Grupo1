package com.inmobiliaria.inmobiliaria_mantencion.client;

import com.inmobiliaria.inmobiliaria_mantencion.dtos.response.PropiedadResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inmobiliaria-propiedad", url = "http://inmobiliaria-propiedad:8082")
public interface PropiedadClient {

    @GetMapping("/api/v1/propiedad/{id}")
    PropiedadResponse obtenerPropiedadPorId(@PathVariable("id") Long id);
}