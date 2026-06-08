package com.inmobiliaria.inmobiliaria_pago.client;

import com.inmobiliaria.inmobiliaria_pago.dtos.response.ContratoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inmobiliaria-contrato", url = "http://inmobiliaria-contrato:8085")
public interface ContratoClient {

    @GetMapping("/api/v1/contratos/{id}")
    ContratoResponse obtenerContratoPorId(@PathVariable("id") Long id);
}