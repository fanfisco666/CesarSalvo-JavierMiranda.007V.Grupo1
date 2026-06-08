package com.inmobiliaria.inmobiliaria_contrato.models;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "contrato")
public class ContratoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idContrato")
    private Long idContrato;

    @Column(name = "idCliente", nullable = false)
    private Long idCliente;

    @Column(name = "idPropiedad", nullable = false)
    private Long idPropiedad;

    @Column(name = "idAgente", nullable = false)
    private Long idAgente;

    @Column(name = "idVisita", nullable = false)
    private Long idVisita;

    @Column(name = "tipoContrato", nullable = false, length = 50)
    private String tipoContrato;

    @Column(name = "montoTotal", nullable = false)
    private BigDecimal montoTotal;

    @Column(name = "fechaInicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fechaFin")
    private LocalDate fechaFin;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "observaciones", length = 500)
    private String observaciones;
}