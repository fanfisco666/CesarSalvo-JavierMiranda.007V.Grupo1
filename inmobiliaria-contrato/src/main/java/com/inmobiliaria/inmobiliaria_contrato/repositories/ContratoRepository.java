package com.inmobiliaria.inmobiliaria_contrato.repositories;

import com.inmobiliaria.inmobiliaria_contrato.models.ContratoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<ContratoModel, Long> {

    List<ContratoModel> findByIdCliente(Long idCliente);
    List<ContratoModel> findByIdPropiedad(Long idPropiedad);
    List<ContratoModel> findByEstado(String estado);
    List<ContratoModel> findByTipoContrato(String tipoContrato);

    // Verificar si propiedad ya tiene contrato vigente
    @Query("SELECT COUNT(c) > 0 FROM ContratoModel c WHERE c.idPropiedad = :idPropiedad AND c.estado = 'VIGENTE'")
    boolean existeContratoVigenteParaPropiedad(@Param("idPropiedad") Long idPropiedad);

    // Verificar si visita ya tiene contrato
    @Query("SELECT COUNT(c) > 0 FROM ContratoModel c WHERE c.idVisita = :idVisita")
    boolean existeContratoParaVisita(@Param("idVisita") Long idVisita);
}