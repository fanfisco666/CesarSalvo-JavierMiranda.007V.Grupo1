package com.inmobiliaria.inmobiliaria_cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class InmobiliariaClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(InmobiliariaClienteApplication.class, args);
	}

}
