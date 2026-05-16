package com.inmobiliaria.inmobiliaria_agente.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.inmobiliaria.inmobiliaria_agente.dtos.request.AgenteRequest;
import com.inmobiliaria.inmobiliaria_agente.dtos.response.AgenteResponse;
import com.inmobiliaria.inmobiliaria_agente.exceptions.NotFoundExceptions;
import com.inmobiliaria.inmobiliaria_agente.models.AgenteModel;
import com.inmobiliaria.inmobiliaria_agente.repositories.AgenteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AgenteService {

    @Autowired
    private AgenteRepository agenteRepository;

    public AgenteService(AgenteRepository agenteRepository) {
        this.agenteRepository = agenteRepository;
    }

    // para listar todos los agentes
    public List<AgenteResponse> obtenerTodos() {
        List<AgenteModel> agentes = agenteRepository.findAll();
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
        AgenteModel modelo = new AgenteModel();
        modelo.setRutAgente(dto.getRutAgente());
        modelo.setNombreAgente(dto.getNombreAgente());
        modelo.setApellidosAgente(dto.getApellidosAgente());
        modelo.setCorreoAgente(dto.getCorreoAgente());

        AgenteModel agenteGuardado = agenteRepository.save(modelo);
        return AgenteResponse.builder()
                .idAgente(agenteGuardado.getIdAgente())
                .rutAgente(agenteGuardado.getRutAgente())
                .nombreAgente(agenteGuardado.getNombreAgente())
                .apellidosAgente(agenteGuardado.getApellidosAgente())
                .correoAgente(agenteGuardado.getCorreoAgente())
                .build();

    }

    public AgenteResponse actualizar(Long id, AgenteRequest dto) {
        AgenteModel agente = agenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptions("Agente con ID " + id + " no encontrado"));
        agente.setRutAgente(dto.getRutAgente());
        agente.setNombreAgente(dto.getNombreAgente());
        agente.setApellidosAgente(dto.getApellidosAgente());
        agente.setCorreoAgente(dto.getCorreoAgente());

        AgenteModel agenteActualizado = agenteRepository.save(agente);
        return AgenteResponse.builder()
                .idAgente(agenteActualizado.getIdAgente())
                .rutAgente(agenteActualizado.getRutAgente())
                .nombreAgente(agenteActualizado.getNombreAgente())
                .apellidosAgente(agenteActualizado.getApellidosAgente())
                .correoAgente(agenteActualizado.getCorreoAgente())
                .build();
    }

    // para eliminar un agente
    public void eliminar(@NonNull Long id) {
        AgenteModel agente = agenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptions("Agente con ID " + id + " no encontrado"));
        agenteRepository.delete(agente);
    }
}