package com.inmobiliaria.inmobiliaria_notificacion.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "NOTIFICACION")
public class NotificacionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDNOTIFICACION")
    private Long idNotificacion;

    @Column(name = "IDCLIENTE", nullable = false)
    private Long idCliente;

    @Column(name = "TIPO", nullable = false, length = 100)
    private String tipo;

    @Column(name = "MENSAJE", nullable = false, length = 500)
    private String mensaje;

    @Column(name = "FECHAENVIO", nullable = false)
    private LocalDateTime fechaEnvio;

    @Column(name = "LEIDA", nullable = false)
    private Boolean leida;

    @Column(name = "REFERENCIA", length = 100)
    private String referencia;
}