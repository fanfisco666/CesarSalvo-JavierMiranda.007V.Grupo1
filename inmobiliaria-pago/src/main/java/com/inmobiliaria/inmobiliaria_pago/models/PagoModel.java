package com.inmobiliaria.inmobiliaria_pago.models;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "pago")
public class PagoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPago")
    private Long idPago;

    @Column(name = "idContrato", nullable = false)
    private Long idContrato;

    @Column(name = "idCliente", nullable = false)
    private Long idCliente;

    @Column(name = "montoPago", nullable = false)
    private BigDecimal montoPago;

    @Column(name = "fechaPago", nullable = false)
    private LocalDate fechaPago;

    @Column(name = "fechaVencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "metodoPago", nullable = false, length = 50)
    private String metodoPago;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "numeroCuota")
    private Integer numeroCuota;

    @Column(name = "observaciones", length = 500)
    private String observaciones;
}
