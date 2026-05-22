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
@Table(name = "MANTENCION")
public class MantencionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDMANTENCION")
    private Long idMantencion;

    @Column(name = "DESCRIPCION", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "ESTADO", nullable = false, length = 50)
    private String estado; // Puede ser "pendiente", "en progreso", "completado"

    @Column(name = "Fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "Fecha_fin")
    private LocalDate fechaFin;

    // informacion que vienne de otros microservicios

    @Column(name = "IDAGENTE", nullable = false)
    private Long idAgente; // ID del agente inmobiliario asignado a la mantención

    @Column(name = "IDPROPIEDAD", nullable = false)
    private Long idPropiedad; // ID de la propiedad relacionada con la mantención

}
