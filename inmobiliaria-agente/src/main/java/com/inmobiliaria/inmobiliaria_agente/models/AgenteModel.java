package com.inmobiliaria.inmobiliaria_agente.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "agente")
public class AgenteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAgente")
    private Long idAgente;

    @Column(name = "rutAgente", nullable = false, unique = true, length = 13)
    private String rutAgente;

    @Column(name = "nombreAgente", nullable = false, length = 100)
    private String nombreAgente;

    @Column(name = "apellidosAgente", nullable = false, length = 150)
    private String apellidosAgente;

    @Column(name = "correoAgente", nullable = false, length = 150)
    private String correoAgente;

}
