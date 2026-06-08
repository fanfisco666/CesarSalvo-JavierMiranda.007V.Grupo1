package com.inmobiliaria.inmobiliaria_cliente.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cliente")
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCliente")
    private Long idCliente;

    @Column(name = "rutCliente",nullable = false, unique = true, length = 13)
    private String rutCliente;

    @Column(name = "nombreCliente",nullable = false, length = 100)
    private String nombreCliente;

    @Column(name = "apellidosCliente", nullable = false, length = 150)
    private String apellidosCliente;

    @Column(name = "correoCliente",nullable = false, length = 150)
    private String correoCliente;
}
