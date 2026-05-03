package com.inmobiliaria.inmobiliaria_cliente.repositories;

import com.inmobiliaria.inmobiliaria_cliente.models.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {

    Optional<ClienteModel> findByRut(String rut);
    boolean existsByRut(String rut);
}
