package com.inmobiliaria.inmobiliaria_propiedad.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "PROPIEDAD")
public class PropiedadModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPROPIEDAD")
    private Long idPropiedad;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false) // el tipo de propiedad como departamento, casa, etc.
    private String tipo;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private boolean estado;

    @Column(name = "superficie_m2", nullable = false)
    private Double superficie;

    @Column(name = "habitaciones", nullable = false)
    private Integer habitacion;

    @Column(name = "baños", nullable = false)
    private Integer banno;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

}
