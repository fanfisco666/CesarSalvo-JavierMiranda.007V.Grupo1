package com.inmobiliaria.inmobiliaria_auth.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "USUARIO")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDUSUARIO")
    private long idUsuario;

    @Column(name = "USERNAME", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;

    // ADMIN, AGENTE, CLIENTE
    @Column(name = "ROL", nullable = false, length = 50)
    private String rol;

    @Column(name = "ACTIVO", nullable = false)
    private Boolean activo;
}
