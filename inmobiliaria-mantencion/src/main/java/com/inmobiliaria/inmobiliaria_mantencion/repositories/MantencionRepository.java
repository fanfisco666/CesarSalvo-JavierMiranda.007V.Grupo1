package com.inmobiliaria.inmobiliaria_mantencion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmobiliaria.inmobiliaria_mantencion.model.MantencionModel;

@Repository
public interface MantencionRepository extends JpaRepository<MantencionModel, Long> {

    // buscar mantenciones por estado
    List<MantencionModel> findByEstado(String estado);

    // buscar mantenciones por idAgente
    List<MantencionModel> findByIdAgente(Long idAgente);

    // buscar mantenciones por idPropiedad
    List<MantencionModel> findByIdPropiedad(Long idPropiedad);

}
