package com.inmobiliaria.inmobiliaria_reporte.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmobiliaria.inmobiliaria_reporte.models.ReporteModel;

@Repository
public interface ReporteRepository extends JpaRepository<ReporteModel, Long> {

}
