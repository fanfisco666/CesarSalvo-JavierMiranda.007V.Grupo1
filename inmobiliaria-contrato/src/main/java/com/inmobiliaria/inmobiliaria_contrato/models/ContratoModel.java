package com.inmobiliaria.inmobiliaria_contrato.models;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "CONTRATO")
public class ContratoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCONTRATO")
    private Long idContrato;

    @Column(name = "IDCLIENTE", nullable = false)
    private Long idCliente;

    @Column(name = "IDPROPIEDAD", nullable = false)
    private Long idPropiedad;

    @Column(name = "IDAGENTE", nullable = false)
    private Long idAgente;

    @Column(name = "IDVISITA", nullable = false)
    private Long idVisita;

    @Column(name = "TIPOCONTRATO", nullable = false, length = 50)
    private String tipoContrato;

    @Column(name = "MONTOTOTAL", nullable = false)
    private BigDecimal montoTotal;

    @Column(name = "FECHAINICIO", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "FECHAFIN")
    private LocalDate fechaFin;

    @Column(name = "ESTADO", nullable = false, length = 50)
    private String estado;

    @Column(name = "OBSERVACIONES", length = 500)
    private String observaciones;
}