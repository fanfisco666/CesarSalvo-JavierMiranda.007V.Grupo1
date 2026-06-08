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
@Table(name = "propiedad")
public class PropiedadModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPropiedad")
    private Long idPropiedad;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "tipo", nullable = false) // el tipo de propiedad como departamento, casa, etc.
    private String tipo;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    @Column(name = "superficie_m2", nullable = false)
    private Double superficie;

    @Column(name = "habitaciones", nullable = false)
    private Integer habitacion;

    @Column(name = "bannos", nullable = false)
    private Integer banno;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

}
