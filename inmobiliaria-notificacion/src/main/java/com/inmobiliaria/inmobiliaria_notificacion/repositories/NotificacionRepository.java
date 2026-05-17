package com.inmobiliaria.inmobiliaria_notificacion.repositories;

import com.inmobiliaria.inmobiliaria_notificacion.models.NotificacionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<NotificacionModel, Long> {

    List<NotificacionModel> findByIdCliente(Long idCliente);
    List<NotificacionModel> findByLeida(Boolean leida);
    List<NotificacionModel> findByTipo(String tipo);

    // Notificaciones no leídas por cliente
    @Query("SELECT n FROM NotificacionModel n WHERE n.idCliente = :idCliente AND n.leida = false")
    List<NotificacionModel> findNoLeidasPorCliente(@Param("idCliente") Long idCliente);

    // Contar no leídas por cliente
    @Query("SELECT COUNT(n) FROM NotificacionModel n WHERE n.idCliente = :idCliente AND n.leida = false")
    Long contarNoLeidasPorCliente(@Param("idCliente") Long idCliente);
}