package com.inmobiliaria.inmobiliaria_mantencion.service;

import java.util.List;
import java.util.stream.Collectors;

import com.inmobiliaria.inmobiliaria_mantencion.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.inmobiliaria.inmobiliaria_mantencion.client.AgenteClient;
import com.inmobiliaria.inmobiliaria_mantencion.client.PropiedadClient;
import com.inmobiliaria.inmobiliaria_mantencion.dtos.request.MantencionRequest;
import com.inmobiliaria.inmobiliaria_mantencion.dtos.response.MantencionResponse;
import com.inmobiliaria.inmobiliaria_mantencion.model.MantencionModel;
import com.inmobiliaria.inmobiliaria_mantencion.repositories.MantencionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
public class MantencionService {

    private static final Logger log = LoggerFactory.getLogger(MantencionService.class);

    private final MantencionRepository mantencionRepository;
    private final AgenteClient agenteClient;
    private final PropiedadClient propiedadClient;

    public MantencionService(MantencionRepository mantencionRepository,
                         PropiedadClient propiedadClient,
                         AgenteClient agenteClient){
        this.mantencionRepository = mantencionRepository;
        this.propiedadClient = propiedadClient;
        this.agenteClient = agenteClient;
    }

    public MantencionResponse crearMantencion(MantencionRequest request) {
        log.info("Creando mantencion para propiedad ID: {} con agente ID: {}",
                request.getIdPropiedad(), request.getIdAgente());

        MantencionModel model = new MantencionModel();
        model.setDescripcion(request.getDescripcion());
        model.setEstado(request.getEstado());
        model.setFechaInicio(request.getFechaInicio());
        model.setFechaFin(request.getFechaFin());
        model.setIdAgente(request.getIdAgente());
        model.setIdPropiedad(request.getIdPropiedad());

        MantencionModel guardado = mantencionRepository.save(model);
        log.info("Mantencion creada exitosamente con ID: {}", guardado.getIdMantencion());
        return mapToResponse(guardado);
    }

    public MantencionResponse obtenerMantencionPorId(Long id) {
        log.info("Buscando mantencion con ID: {}", id);
        MantencionModel model = mantencionRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Mantencion con ID {} no encontrada", id);
                    return new NotFoundException("Mantencion con ID " + id + " no encontrada");
                });
        log.info("Mantencion con ID {} encontrada", id);
        return mapToResponse(model);
    }

    public List<MantencionResponse> obtenerTodasLasMantenciones() {
        log.info("Obteniendo todas las mantenciones");
        List<MantencionModel> mantenciones = mantencionRepository.findAll();
        log.info("Se encontraron {} mantenciones", mantenciones.size());
        return mantenciones.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<MantencionResponse> obtenerMantencionesPorEstado(String estado) {
        log.info("Obteniendo mantenciones con estado: {}", estado);
        List<MantencionModel> mantenciones = mantencionRepository.findByEstado(estado);
        log.info("Se encontraron {} mantenciones con estado {}", mantenciones.size(), estado);
        return mantenciones.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MantencionResponse actualizarMantencion(Long id, MantencionRequest request) {
        log.info("Actualizando mantencion con ID: {}", id);
        MantencionModel model = mantencionRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Mantencion con ID {} no encontrada", id);
                    return new NotFoundException("Mantencion con ID " + id + " no encontrada");
                });

        model.setDescripcion(request.getDescripcion());
        model.setEstado(request.getEstado());
        model.setFechaInicio(request.getFechaInicio());
        model.setFechaFin(request.getFechaFin());
        model.setIdAgente(request.getIdAgente());
        model.setIdPropiedad(request.getIdPropiedad());

        MantencionModel actualizado = mantencionRepository.save(model);
        log.info("Mantencion con ID {} actualizada exitosamente", id);
        return mapToResponse(actualizado);
    }

    public void eliminarMantencion(Long id) {
        log.warn("Eliminando mantencion con ID: {}", id);
        MantencionModel model = mantencionRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Mantencion con ID {} no encontrada", id);
                    return new NotFoundException("Mantencion con ID " + id + " no encontrada");
                });
        mantencionRepository.delete(model);
        log.info("Mantencion con ID {} eliminada exitosamente", id);
    }

    private MantencionResponse mapToResponse(MantencionModel model) {
        log.info("Consultando agente ID: {} y propiedad ID: {} via Feign",
                model.getIdAgente(), model.getIdPropiedad());
        try {
            return MantencionResponse.builder()
                    .idMantencion(model.getIdMantencion())
                    .descripcion(model.getDescripcion())
                    .estado(model.getEstado())
                    .fechaInicio(model.getFechaInicio())
                    .fechaFin(model.getFechaFin())
                    .agente(agenteClient.obtenerAgentePorId(model.getIdAgente()))
                    .propiedad(propiedadClient.obtenerPropiedadPorId(model.getIdPropiedad()))
                    .build();
        } catch (Exception e) {
            log.warn("No se pudo obtener agente o propiedad via Feign para mantencion ID: {}. Error: {}",
                    model.getIdMantencion(), e.getMessage());
            return MantencionResponse.builder()
                    .idMantencion(model.getIdMantencion())
                    .descripcion(model.getDescripcion())
                    .estado(model.getEstado())
                    .fechaInicio(model.getFechaInicio())
                    .fechaFin(model.getFechaFin())
                    .agente(null)
                    .propiedad(null)
                    .build();
        }
    }
}