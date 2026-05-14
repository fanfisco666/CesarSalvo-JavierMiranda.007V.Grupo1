package com.inmobiliaria.inmobiliaria_visita.client;

import com.inmobiliaria.inmobiliaria_visita.dtos.response.AgenteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inmobiliaria-agente", url = "http://localhost:8083")
public interface AgenteClient {

    @GetMapping("/api/v1/agentes/{id}")
    AgenteResponse obtenerAgentePorId(@PathVariable("id") Long id);
}