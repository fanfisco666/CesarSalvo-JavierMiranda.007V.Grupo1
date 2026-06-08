package com.inmobiliaria.inmobiliaria_agente.services;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.inmobiliaria.inmobiliaria_agente.dtos.request.AgenteRequest;
import com.inmobiliaria.inmobiliaria_agente.dtos.response.AgenteResponse;
import com.inmobiliaria.inmobiliaria_agente.exceptions.NotFoundExceptions;
import com.inmobiliaria.inmobiliaria_agente.models.AgenteModel;
import com.inmobiliaria.inmobiliaria_agente.repositories.AgenteRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class AgenteService {

    private final AgenteRepository agenteRepository;

    public AgenteService(AgenteRepository agenteRepository) {
        this.agenteRepository = agenteRepository;
    }

    // para listar todos los agentes
    public List<AgenteResponse> obtenerTodos() {
        log.info("Obteniendo lista de todos los agentes");
        List<AgenteModel> agentes = agenteRepository.findAll();
        log.info("Se encontraron {} agentes", agentes.size());
        return agentes.stream().map(agente -> AgenteResponse.builder()
                .idAgente(agente.getIdAgente())
                .rutAgente(agente.getRutAgente())
                .nombreAgente(agente.getNombreAgente())
                .apellidosAgente(agente.getApellidosAgente())
                .correoAgente(agente.getCorreoAgente())
                .build()).toList();
    }

    // para genererar y actualizar un agente
    public AgenteResponse guardar(AgenteRequest dto) {
        log.info("Guardando nuevo agente");
        log.debug("Datos Recibidos. RUT={}", dto.getRutAgente());
        AgenteModel modelo = new AgenteModel();
        modelo.setRutAgente(dto.getRutAgente());
        modelo.setNombreAgente(dto.getNombreAgente());
        modelo.setApellidosAgente(dto.getApellidosAgente());
        modelo.setCorreoAgente(dto.getCorreoAgente());

        AgenteModel agenteGuardado = agenteRepository.save(modelo);
        log.info("Agente guardado con ID: {}", agenteGuardado.getIdAgente());
        return AgenteResponse.builder()
                .idAgente(agenteGuardado.getIdAgente())
                .rutAgente(agenteGuardado.getRutAgente())
                .nombreAgente(agenteGuardado.getNombreAgente())
                .apellidosAgente(agenteGuardado.getApellidosAgente())
                .correoAgente(agenteGuardado.getCorreoAgente())
                .build();

    }

    public AgenteResponse actualizar(Long id, AgenteRequest dto) {
        log.info("Actualizando agente con ID: {}", id);
        log.debug("Datos Recibidos para actualización. ID={}, RUT={}", id, dto.getRutAgente());
        AgenteModel agente = agenteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Agente no encontrado. ID={}", id);
                    return new NotFoundExceptions("Agente con ID " + id + " no encontrado");
                });
        agente.setRutAgente(dto.getRutAgente());
        agente.setNombreAgente(dto.getNombreAgente());
        agente.setApellidosAgente(dto.getApellidosAgente());
        agente.setCorreoAgente(dto.getCorreoAgente());

        AgenteModel agenteActualizado = agenteRepository.save(agente);
        log.info("Agente con ID {} actualizado", id);
        return AgenteResponse.builder()
                .idAgente(agenteActualizado.getIdAgente())
                .rutAgente(agenteActualizado.getRutAgente())
                .nombreAgente(agenteActualizado.getNombreAgente())
                .apellidosAgente(agenteActualizado.getApellidosAgente())
                .correoAgente(agenteActualizado.getCorreoAgente())
                .build();
    }

    public AgenteResponse obtenerPorId(Long id) {
        log.info("Buscando agente con ID: {}", id);

        AgenteModel agente = agenteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Agente no encontrado. ID={}", id);
                    return new NotFoundExceptions("Agente con ID " + id + " no encontrado");
                });

        return AgenteResponse.builder()
                .idAgente(agente.getIdAgente())
                .rutAgente(agente.getRutAgente())
                .nombreAgente(agente.getNombreAgente())
                .apellidosAgente(agente.getApellidosAgente())
                .correoAgente(agente.getCorreoAgente())
                .build();
    }

    // para eliminar un agente
    public void eliminar(@NonNull Long id) {
        log.info("Eliminando agente con ID: {}", id);
        AgenteModel agente = agenteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Agente no encontrado. ID={}", id);
                    return new NotFoundExceptions("Agente con ID " + id + " no encontrado");
                });
        agenteRepository.delete(agente);
        log.info("Agente con ID {} eliminado", id);
    }
}