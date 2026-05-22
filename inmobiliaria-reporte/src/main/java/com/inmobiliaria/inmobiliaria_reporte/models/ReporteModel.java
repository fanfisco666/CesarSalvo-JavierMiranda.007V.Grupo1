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
@Table(name = "REPORTE")
public class ReporteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDREPORTE")
    private Long idReporte;

    @Column(name = "TITULO", nullable = false)
    private String titulo;

    @Column(name = "DESCRIPCION", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "TIPOREPORTE", nullable = false)
    private String tipoReporte; // Puede ser mantenimiento, queja.

    @Column(name = "IDPROPIEDAD", nullable = false)
    private Long idPropiedad; // ID de la propiedad relacionada con el reporte

    @Column(name = "IDUSUARIO", nullable = false)
    private Long idUsuario; // ID del cliente que hizo el reporte

    @Column(name = "IDAGENTE", nullable = false)
    private Long idAgente; // ID del agente inmobiliario asignado al reporte

}
