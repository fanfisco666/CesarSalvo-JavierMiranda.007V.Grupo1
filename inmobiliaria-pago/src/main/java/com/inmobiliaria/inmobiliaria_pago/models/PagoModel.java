package com.inmobiliaria.inmobiliaria_pago.models;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "PAGO")
public class PagoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPAGO")
    private Long idPago;

    @Column(name = "IDCONTRATO", nullable = false)
    private Long idContrato;

    @Column(name = "IDCLIENTE", nullable = false)
    private Long idCliente;

    @Column(name = "MONTOPAGO", nullable = false)
    private BigDecimal montoPago;

    @Column(name = "FECHAPAGO", nullable = false)
    private LocalDate fechaPago;

    @Column(name = "FECHAVENCIMIENTO", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "METODOPAGO", nullable = false, length = 50)
    private String metodoPago;

    @Column(name = "ESTADO", nullable = false, length = 50)
    private String estado;

    @Column(name = "NUMEROCUOTA")
    private Integer numeroCuota;

    @Column(name = "OBSERVACIONES", length = 500)
    private String observaciones;
}
