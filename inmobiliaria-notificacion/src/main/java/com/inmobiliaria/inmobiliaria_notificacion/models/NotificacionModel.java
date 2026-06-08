package com.inmobiliaria.inmobiliaria_notificacion.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notificacion")
public class NotificacionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idNotificacion")
    private Long idNotificacion;

    @Column(name = "idCliente", nullable = false)
    private Long idCliente;

    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;

    @Column(name = "mensaje", nullable = false, length = 500)
    private String mensaje;

    @Column(name = "fechaEnvio", nullable = false)
    private LocalDateTime fechaEnvio;

    @Column(name = "leida", nullable = false)
    private Boolean leida;

    @Column(name = "referencia", length = 100)
    private String referencia;
}