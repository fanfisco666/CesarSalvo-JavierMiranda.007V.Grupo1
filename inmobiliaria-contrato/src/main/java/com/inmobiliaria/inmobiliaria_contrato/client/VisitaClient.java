package com.inmobiliaria.inmobiliaria_contrato.client;

import com.inmobiliaria.inmobiliaria_contrato.dtos.response.VisitaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inmobiliaria-visita", url = "http://localhost:8084")
public interface VisitaClient {

    @GetMapping("/api/v1/visitas/{id}")
    VisitaResponse obtenerVisitaPorId(@PathVariable("id") Long id);
}