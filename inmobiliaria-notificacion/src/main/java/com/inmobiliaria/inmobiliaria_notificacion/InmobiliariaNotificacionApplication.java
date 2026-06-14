package com.inmobiliaria.inmobiliaria_notificacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InmobiliariaNotificacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(InmobiliariaNotificacionApplication.class, args);
	}

}
