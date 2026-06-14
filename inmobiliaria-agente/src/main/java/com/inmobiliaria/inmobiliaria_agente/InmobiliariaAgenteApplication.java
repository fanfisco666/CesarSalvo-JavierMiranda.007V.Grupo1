package com.inmobiliaria.inmobiliaria_agente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class InmobiliariaAgenteApplication {

	public static void main(String[] args) {
		SpringApplication.run(InmobiliariaAgenteApplication.class, args);
	}

}
