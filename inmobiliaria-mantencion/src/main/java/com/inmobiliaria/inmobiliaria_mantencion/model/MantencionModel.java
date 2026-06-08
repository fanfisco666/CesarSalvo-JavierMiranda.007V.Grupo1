package com.inmobiliaria.inmobiliaria_mantencion.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "mantencion")
public class MantencionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMantencion")
    private Long idMantencion;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado; // Puede ser "pendiente", "en progreso", "completado"

    @Column(name = "fechaInicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fechaFin")
    private LocalDate fechaFin;

    // informacion que vienne de otros microservicios

    @Column(name = "idAgente", nullable = false)
    private Long idAgente; // ID del agente inmobiliario asignado a la mantención

    @Column(name = "idPropiedad", nullable = false)
    private Long idPropiedad; // ID de la propiedad relacionada con la mantención

}
