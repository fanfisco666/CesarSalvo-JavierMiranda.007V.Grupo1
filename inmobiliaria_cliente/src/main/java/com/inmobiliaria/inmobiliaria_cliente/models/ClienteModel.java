package com.inmobiliaria.inmobiliaria_cliente.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CLIENTE")
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCLIENTE")
    private Long idCliente;

    @Column(name = "RUTCLIENTE",nullable = false, unique = true, length = 13)
    private String rutCliente;

    @Column(name = "NOMBRECLIENTE",nullable = false, length = 100)
    private String nombreCliente;

    @Column(name = "APELLIDOSCLIENTE", nullable = false, length = 150)
    private String apellidosCliente;

    @Column(name = "CORREOCLIENTE",nullable = false, length = 150)
    private String correoCliente;
}
