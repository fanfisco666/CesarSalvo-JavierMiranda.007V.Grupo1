package com.inmobiliaria.inmobiliaria_visita.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "VISITA")
public class VisitaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDVISITA")
    private Long idVisita;

    @Column(name = "IDCLIENTE", nullable = false)
    private Long idCliente;

    @Column(name = "IDPROPIEDAD", nullable = false)
    private Long idPropiedad;

    @Column(name = "IDAGENTE", nullable = false)
    private Long idAgente;

    @Column(name = "FECHAVISITA", nullable = false)
    private LocalDate fechaVisita;

    @Column(name = "HORAVISITA", nullable = false)
    private LocalTime horaVisita;

    @Column(name = "ESTADO", nullable = false, length = 50)
    private String estado;

    @Column(name = "OBSERVACIONES", length = 500)
    private String observaciones;
}