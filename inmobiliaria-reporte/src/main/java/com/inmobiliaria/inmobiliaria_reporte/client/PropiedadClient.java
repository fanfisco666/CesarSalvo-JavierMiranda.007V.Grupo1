package com.inmobiliaria.inmobiliaria_reporte.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.inmobiliaria.inmobiliaria_reporte.dtos.response.PropiedadResponse;

@FeignClient(name = "inmobiliaria-propiedad", url = "http://inmobiliaria-propiedad:8082")
public interface PropiedadClient {

    @GetMapping("/api/v1/propiedad/{id}")
    PropiedadResponse obtenerPropiedadPorId(@PathVariable("id") Long id);

}
