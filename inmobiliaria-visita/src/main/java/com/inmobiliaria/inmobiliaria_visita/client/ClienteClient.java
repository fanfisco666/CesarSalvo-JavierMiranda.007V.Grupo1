package com.inmobiliaria.inmobiliaria_visita.client;

import com.inmobiliaria.inmobiliaria_visita.dtos.response.ClienteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inmobiliaria-cliente", url = "http://inmobiliaria-cliente:8081")
public interface ClienteClient {

    @GetMapping("/api/v1/clientes/{id}")
    ClienteResponse obtenerClientePorId(@PathVariable("id") Long id);
}