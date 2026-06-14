package com.inmobiliaria.inmobiliaria_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class InmobiliariaAuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(InmobiliariaAuthApplication.class, args);
	}
}