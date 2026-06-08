package com.inmobiliaria.inmobiliaria_reporte.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "reporte")
public class ReporteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReporte")
    private Long idReporte;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "tipoReporte", nullable = false)
    private String tipoReporte; // Puede ser mantenimiento, queja.

    @Column(name = "idPropiedad", nullable = false)
    private Long idPropiedad; // ID de la propiedad relacionada con el reporte

    @Column(name = "idUsuario", nullable = false)
    private Long idUsuario; // ID del cliente que hizo el reporte

    @Column(name = "idAgente", nullable = false)
    private Long idAgente; // ID del agente inmobiliario asignado al reporte

}
