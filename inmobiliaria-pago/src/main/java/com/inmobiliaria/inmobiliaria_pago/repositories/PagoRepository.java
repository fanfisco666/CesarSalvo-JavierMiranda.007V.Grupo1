package com.inmobiliaria.inmobiliaria_pago.repositories;

import com.inmobiliaria.inmobiliaria_pago.models.PagoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<PagoModel, Long> {

    List<PagoModel> findByIdContrato(Long idContrato);
    List<PagoModel> findByIdCliente(Long idCliente);
    List<PagoModel> findByEstado(String estado);

    // Pagos en mora — vencimiento pasado y no pagados
    @Query("SELECT p FROM PagoModel p WHERE p.fechaVencimiento < :hoy AND p.estado = 'PENDIENTE'")
    List<PagoModel> findPagosEnMora(@Param("hoy") LocalDate hoy);

    // Verificar si cuota ya fue pagada
    @Query("SELECT COUNT(p) > 0 FROM PagoModel p WHERE p.idContrato = :idContrato AND p.numeroCuota = :cuota AND p.estado = 'PAGADO'")
    boolean existeCuotaPagada(@Param("idContrato") Long idContrato, @Param("cuota") Integer cuota);
}