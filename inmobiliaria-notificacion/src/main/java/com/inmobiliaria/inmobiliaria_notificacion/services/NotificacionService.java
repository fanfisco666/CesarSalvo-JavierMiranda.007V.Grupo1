package com.inmobiliaria.inmobiliaria_notificacion.services;

import com.inmobiliaria.inmobiliaria_notificacion.dtos.request.NotificacionRequest;
import com.inmobiliaria.inmobiliaria_notificacion.dtos.response.NotificacionResponse;
import com.inmobiliaria.inmobiliaria_notificacion.exceptions.NotFoundException;
import com.inmobiliaria.inmobiliaria_notificacion.models.NotificacionModel;
import com.inmobiliaria.inmobiliaria_notificacion.repositories.NotificacionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    public List<NotificacionResponse> obtenerTodos() {
        log.info("Obteniendo todas las notificaciones");
        List<NotificacionModel> notificaciones = notificacionRepository.findAll();
        log.info("Se encontraron {} notificaciones", notificaciones.size());
        return notificaciones.stream().map(this::toResponse).toList();
    }

    public NotificacionResponse obtenerPorId(Long id) {
        log.info("Buscando notificacion con ID: {}", id);
        NotificacionModel notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Notificacion con ID {} no encontrada", id);
                    return new NotFoundException("Notificacion con ID " + id + " no encontrada");
                });
        return toResponse(notificacion);
    }

    public List<NotificacionResponse> obtenerPorCliente(Long idCliente) {
        log.info("Obteniendo notificaciones del cliente ID: {}", idCliente);
        return notificacionRepository.findByIdCliente(idCliente)
                .stream().map(this::toResponse).toList();
    }

    public List<NotificacionResponse> obtenerNoLeidasPorCliente(Long idCliente) {
        log.info("Obteniendo notificaciones no leidas del cliente ID: {}", idCliente);
        List<NotificacionModel> noLeidas = notificacionRepository.findNoLeidasPorCliente(idCliente);
        log.info("Cliente ID: {} tiene {} notificaciones no leidas", idCliente, noLeidas.size());
        return noLeidas.stream().map(this::toResponse).toList();
    }

    public Long contarNoLeidas(Long idCliente) {
        log.info("Contando notificaciones no leidas del cliente ID: {}", idCliente);
        Long total = notificacionRepository.contarNoLeidasPorCliente(idCliente);
        log.info("Cliente ID: {} tiene {} notificaciones no leidas", idCliente, total);
        return total;
    }

    public NotificacionResponse guardar(NotificacionRequest dto) {
        log.info("Registrando notificacion tipo: {} para cliente ID: {}",
                dto.getTipo(), dto.getIdCliente());

        NotificacionModel modelo = new NotificacionModel();
        modelo.setIdCliente(dto.getIdCliente());
        modelo.setTipo(dto.getTipo());
        modelo.setMensaje(dto.getMensaje());
        modelo.setFechaEnvio(LocalDateTime.now());
        modelo.setLeida(false);
        modelo.setReferencia(dto.getReferencia());

        NotificacionModel guardada = notificacionRepository.save(modelo);
        log.info("Notificacion registrada con ID: {}", guardada.getIdNotificacion());
        return toResponse(guardada);
    }

    public NotificacionResponse marcarComoLeida(Long id) {
        log.info("Marcando notificacion ID: {} como leida", id);
        NotificacionModel notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Notificacion ID: {} no encontrada", id);
                    return new NotFoundException("Notificacion con ID " + id + " no encontrada");
                });

        if (notificacion.getLeida()) {
            log.warn("Notificacion ID: {} ya estaba marcada como leida", id);
        }

        notificacion.setLeida(true);
        NotificacionModel actualizada = notificacionRepository.save(notificacion);
        log.info("Notificacion ID: {} marcada como leida", id);
        return toResponse(actualizada);
    }

    public void eliminar(Long id) {
        log.warn("Eliminando notificacion con ID: {}", id);
        notificacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Notificacion con ID " + id + " no encontrada"));
        notificacionRepository.deleteById(id);
        log.info("Notificacion con ID {} eliminada", id);
    }

    private NotificacionResponse toResponse(NotificacionModel m) {
        return NotificacionResponse.builder()
                .idNotificacion(m.getIdNotificacion())
                .idCliente(m.getIdCliente())
                .tipo(m.getTipo())
                .mensaje(m.getMensaje())
                .fechaEnvio(m.getFechaEnvio())
                .leida(m.getLeida())
                .referencia(m.getReferencia())
                .build();
    }
}