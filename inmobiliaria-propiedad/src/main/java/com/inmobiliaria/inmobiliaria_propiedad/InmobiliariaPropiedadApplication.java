package com.inmobiliaria.inmobiliaria_propiedad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class InmobiliariaPropiedadApplication {

	public static void main(String[] args) {
		SpringApplication.run(InmobiliariaPropiedadApplication.class, args);
	}

}
