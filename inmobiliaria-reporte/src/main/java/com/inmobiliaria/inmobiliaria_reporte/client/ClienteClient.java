package com.inmobiliaria.inmobiliaria_reporte.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.inmobiliaria.inmobiliaria_reporte.dtos.external.ClienteResponse;

@FeignClient(name = "cliente-service", url = "http://localhost:8081/api/v1/cliente")
public interface ClienteClient {

    @GetMapping("/{id}")
    ClienteResponse obtenerClientePorId(@PathVariable Long id);

}
