package com.inmobiliaria.inmobiliaria_mantencion.client;

import com.inmobiliaria.inmobiliaria_mantencion.dtos.response.AgenteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inmobiliaria-agente", url = "http://inmobiliaria-agente:8083")
public interface AgenteClient {

    @GetMapping("/api/v1/agente/{id}")
    AgenteResponse obtenerAgentePorId(@PathVariable("id") Long id);
}