package com.inmobiliaria.inmobiliaria_visita.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "visita")
public class VisitaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVisita")
    private Long idVisita;

    @Column(name = "idCliente", nullable = false)
    private Long idCliente;

    @Column(name = "idPropiedad", nullable = false)
    private Long idPropiedad;

    @Column(name = "idAgente", nullable = false)
    private Long idAgente;

    @Column(name = "fechaVisita", nullable = false)
    private LocalDate fechaVisita;

    @Column(name = "horaVisita", nullable = false)
    private LocalTime horaVisita;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "observaciones", length = 500)
    private String observaciones;
}