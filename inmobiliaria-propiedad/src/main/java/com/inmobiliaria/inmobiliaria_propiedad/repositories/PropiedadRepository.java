package com.inmobiliaria.inmobiliaria_propiedad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.inmobiliaria.inmobiliaria_propiedad.models.PropiedadModel;

@Repository
public interface PropiedadRepository extends JpaRepository<PropiedadModel, Long> {

}
