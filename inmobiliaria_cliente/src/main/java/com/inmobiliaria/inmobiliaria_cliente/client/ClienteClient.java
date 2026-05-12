package com.inmobiliaria.inmobiliaria_cliente.client;

import org.springframework.cloud.openfeign.FeignClient;




@FeignClient(name = "inmobiliaria-cliente", url = "http://localhost:8081/api/v1/clientes")

public interface ClienteClient {

}
