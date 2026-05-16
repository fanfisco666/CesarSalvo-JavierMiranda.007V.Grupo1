package com.inmobiliaria.inmobiliaria_pago.models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "PAGO")
public class PagoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPAGO")
    private Long idPago;

    @Column(name = "IDCLIENTE")
    private Long idCliente;

    @Column(name = "IDCONTRATO")
    private Long idContrato;




}
