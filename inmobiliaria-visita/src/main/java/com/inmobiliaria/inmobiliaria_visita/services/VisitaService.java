package com.inmobiliaria.inmobiliaria_visita.services;

import com.inmobiliaria.inmobiliaria_visita.client.AgenteClient;
import com.inmobiliaria.inmobiliaria_visita.client.ClienteClient;
import com.inmobiliaria.inmobiliaria_visita.client.PropiedadClient;
import com.inmobiliaria.inmobiliaria_visita.dtos.request.VisitaRequest;
import com.inmobiliaria.inmobiliaria_visita.dtos.response.VisitaResponse;
import com.inmobiliaria.inmobiliaria_visita.exceptions.NotFoundException;
import com.inmobiliaria.inmobiliaria_visita.exceptions.RemoteServiceException;
import com.inmobiliaria.inmobiliaria_visita.models.VisitaModel;
import com.inmobiliaria.inmobiliaria_visita.repositories.VisitaRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class VisitaService {

    private static final Logger log = LoggerFactory.getLogger(VisitaService.class);

    private final VisitaRepository visitaRepository;
    private final ClienteClient clienteClient;
    private final PropiedadClient propiedadClient;
    private final AgenteClient agenteClient;

    public VisitaService(VisitaRepository visitaRepository,
                         ClienteClient clienteClient,
                         PropiedadClient propiedadClient,
                         AgenteClient agenteClient) {
        this.visitaRepository = visitaRepository;
        this.clienteClient = clienteClient;
        this.propiedadClient = propiedadClient;
        this.agenteClient = agenteClient;
    }

    public List<VisitaResponse> obtenerTodos() {
        log.info("Obteniendo lista de todas las visitas");
        List<VisitaModel> visitas = visitaRepository.findAll();
        log.info("Se encontraron {} visitas", visitas.size());
        return visitas.stream().map(this::toResponse).toList();
    }

    public VisitaResponse obtenerPorId(Long id) {
        log.info("Buscando visita con ID: {}", id);
        VisitaModel visita = visitaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Visita con ID {} no encontrada", id);
                    return new NotFoundException("Visita con ID " + id + " no encontrada");
                });
        log.info("Visita con ID {} encontrada", id);
        return toResponse(visita);
    }

    public List<VisitaResponse> obtenerPorCliente(Long idCliente) {
        log.info("Obteniendo visitas del cliente ID: {}", idCliente);
        return visitaRepository.findByIdCliente(idCliente)
                .stream().map(this::toResponse).toList();
    }

    public List<VisitaResponse> obtenerPorPropiedad(Long idPropiedad) {
        log.info("Obteniendo visitas de la propiedad ID: {}", idPropiedad);
        return visitaRepository.findByIdPropiedad(idPropiedad)
                .stream().map(this::toResponse).toList();
    }

    public List<VisitaResponse> obtenerPorEstado(String estado) {
        log.info("Obteniendo visitas con estado: {}", estado);
        return visitaRepository.findByEstado(estado)
                .stream().map(this::toResponse).toList();
    }

    public VisitaResponse guardar(VisitaRequest dto) {
        log.info("Agendando nueva visita para cliente ID: {} en propiedad ID: {}",
                dto.getIdCliente(), dto.getIdPropiedad());

        // Regla 1 — validar que cliente existe
        try {
            clienteClient.obtenerClientePorId(dto.getIdCliente());
            log.info("Cliente ID: {} validado", dto.getIdCliente());
        } catch (Exception e) {
            log.warn("Cliente ID: {} no encontrado", dto.getIdCliente());
            throw new RemoteServiceException("Cliente con ID " + dto.getIdCliente() + " no encontrado");
        }

        // Regla 2 — validar que propiedad existe y está disponible
        try {
            var propiedad = propiedadClient.obtenerPropiedadPorId(dto.getIdPropiedad());
            log.info("Propiedad ID: {} validada", dto.getIdPropiedad());
        } catch (Exception e) {
            log.warn("Propiedad ID: {} no encontrada", dto.getIdPropiedad());
            throw new RemoteServiceException("Propiedad con ID " + dto.getIdPropiedad() + " no encontrada");
        }

        // Regla 3 — validar que agente existe
        try {
            agenteClient.obtenerAgentePorId(dto.getIdAgente());
            log.info("Agente ID: {} validado", dto.getIdAgente());
        } catch (Exception e) {
            log.warn("Agente ID: {} no encontrado", dto.getIdAgente());
            throw new RemoteServiceException("Agente con ID " + dto.getIdAgente() + " no encontrado");
        }

        // Regla 4 — no permitir dos visitas el mismo día a la misma propiedad
        if (visitaRepository.existeVisitaEnFecha(dto.getIdPropiedad(), dto.getFechaVisita())) {
            log.warn("Ya existe visita para propiedad ID: {} en fecha: {}",
                    dto.getIdPropiedad(), dto.getFechaVisita());
            throw new RemoteServiceException("Ya existe una visita agendada para esa propiedad en esa fecha");
        }

        VisitaModel modelo = new VisitaModel();
        modelo.setIdCliente(dto.getIdCliente());
        modelo.setIdPropiedad(dto.getIdPropiedad());
        modelo.setIdAgente(dto.getIdAgente());
        modelo.setFechaVisita(dto.getFechaVisita());
        modelo.setHoraVisita(dto.getHoraVisita());
        modelo.setEstado("PENDIENTE");
        modelo.setObservaciones(dto.getObservaciones());

        VisitaModel visitaGuardada = visitaRepository.save(modelo);
        log.info("Visita agendada exitosamente con ID: {}", visitaGuardada.getIdVisita());
        return toResponse(visitaGuardada);
    }

    public VisitaResponse actualizar(Long id, VisitaRequest dto) {
        log.info("Actualizando visita con ID: {}", id);
        VisitaModel visita = visitaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Visita con ID {} no encontrada para actualizar", id);
                    return new NotFoundException("Visita con ID " + id + " no encontrada");
                });

        visita.setIdCliente(dto.getIdCliente());
        visita.setIdPropiedad(dto.getIdPropiedad());
        visita.setIdAgente(dto.getIdAgente());
        visita.setFechaVisita(dto.getFechaVisita());
        visita.setHoraVisita(dto.getHoraVisita());
        visita.setObservaciones(dto.getObservaciones());

        VisitaModel visitaActualizada = visitaRepository.save(visita);
        log.info("Visita con ID {} actualizada exitosamente", id);
        return toResponse(visitaActualizada);
    }

    public VisitaResponse cambiarEstado(Long id, String nuevoEstado) {
        log.info("Cambiando estado de visita ID: {} a {}", id, nuevoEstado);
        VisitaModel visita = visitaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Visita con ID {} no encontrada", id);
                    return new NotFoundException("Visita con ID " + id + " no encontrada");
                });

        visita.setEstado(nuevoEstado);
        VisitaModel visitaActualizada = visitaRepository.save(visita);
        log.info("Estado de visita ID: {} cambiado a {}", id, nuevoEstado);
        return toResponse(visitaActualizada);
    }

    public void eliminar(Long id) {
        log.warn("Eliminando visita con ID: {}", id);
        visitaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Visita con ID " + id + " no encontrada"));
        visitaRepository.deleteById(id);
        log.info("Visita con ID {} eliminada", id);
    }

    private VisitaResponse toResponse(VisitaModel m) {
        return VisitaResponse.builder()
                .idVisita(m.getIdVisita())
                .idCliente(m.getIdCliente())
                .idPropiedad(m.getIdPropiedad())
                .idAgente(m.getIdAgente())
                .fechaVisita(m.getFechaVisita())
                .horaVisita(m.getHoraVisita())
                .estado(m.getEstado())
                .observaciones(m.getObservaciones())
                .build();
    }
}