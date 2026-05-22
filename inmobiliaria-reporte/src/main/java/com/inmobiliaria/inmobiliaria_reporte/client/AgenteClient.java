package com.inmobiliaria.inmobiliaria_reporte.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.inmobiliaria.inmobiliaria_reporte.dtos.external.AgenteResponse;

@FeignClient(name = "agente-service", url = "http://localhost:8083/api/v1/agente")
public interface AgenteClient {

    @GetMapping("/{id}")
    AgenteResponse obtenerAgentePorId(@PathVariable Long id);

}
