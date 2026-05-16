package com.inmobiliaria.inmobiliaria_agente.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmobiliaria.inmobiliaria_agente.models.AgenteModel;

@Repository
public interface AgenteRepository extends JpaRepository<AgenteModel, Long> {

    Optional<AgenteModel> findByRutAgente(String rutAgente);

    boolean existsByRutAgente(String rutAgente);

}
