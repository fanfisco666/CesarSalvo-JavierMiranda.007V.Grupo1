package com.inmobiliaria.inmobiliaria_propiedad.Client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "inmobiliaria-agente", url = "http://localhost:8082/api/v1/agentes")
public interface PropiedadClient {

}
