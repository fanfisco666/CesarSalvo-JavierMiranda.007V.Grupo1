package com.inmobiliaria.inmobiliaria_visita.repositories;

import com.inmobiliaria.inmobiliaria_visita.models.VisitaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitaRepository extends JpaRepository<VisitaModel, Long> {

    // Query Methods
    List<VisitaModel> findByIdCliente(Long idCliente);
    List<VisitaModel> findByIdPropiedad(Long idPropiedad);
    List<VisitaModel> findByIdAgente(Long idAgente);
    List<VisitaModel> findByEstado(String estado);

    // Custom Query — verificar si ya existe visita en esa fecha y propiedad
    @Query("SELECT COUNT(v) > 0 FROM VisitaModel v WHERE v.idPropiedad = :idPropiedad AND v.fechaVisita = :fecha AND v.estado != 'CANCELADA'")
    boolean existeVisitaEnFecha(@Param("idPropiedad") Long idPropiedad, @Param("fecha") LocalDate fecha);
}