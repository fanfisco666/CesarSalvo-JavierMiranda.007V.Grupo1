package com.inmobiliaria.inmobiliaria_mantencion.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.inmobiliaria.inmobiliaria_mantencion.client.AgenteClient;
import com.inmobiliaria.inmobiliaria_mantencion.client.PropiedadClient;
import com.inmobiliaria.inmobiliaria_mantencion.dtos.request.MantencionRequest;
import com.inmobiliaria.inmobiliaria_mantencion.dtos.response.MantencionResponse;
import com.inmobiliaria.inmobiliaria_mantencion.exceptions.MantencionNotFoundException;
import com.inmobiliaria.inmobiliaria_mantencion.model.MantencionModel;
import com.inmobiliaria.inmobiliaria_mantencion.repositories.MantencionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MantencionService {

    private final MantencionRepository mantencionRepository;
    private final AgenteClient agenteClient;
    private final PropiedadClient propiedadClient;

    public MantencionResponse crearMantencion(MantencionRequest request) {
        MantencionModel model = new MantencionModel();
        model.setDescripcion(request.getDescripcion());
        model.setEstado(request.getEstado());
        model.setFechaInicio(request.getFechaInicio());
        model.setFechaFin(request.getFechaFin());
        model.setIdAgente(request.getIdAgente());
        model.setIdPropiedad(request.getIdPropiedad());

        MantencionModel guardado = mantencionRepository.save(model);
        return mapToResponse(guardado);
    }

    public MantencionResponse obtenerMantencionPorId(Long id) {
        MantencionModel model = mantencionRepository.findById(id)
                .orElseThrow(() -> new MantencionNotFoundException(id));
        return mapToResponse(model);
    }

    public List<MantencionResponse> obtenerTodasLasMantenciones() {
        return mantencionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<MantencionResponse> obtenerMantencionesPorEstado(String estado) {
        return mantencionRepository.findByEstado(estado)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MantencionResponse actualizarMantencion(Long id, MantencionRequest request) {
        MantencionModel model = mantencionRepository.findById(id)
                .orElseThrow(() -> new MantencionNotFoundException(id));

        model.setDescripcion(request.getDescripcion());
        model.setEstado(request.getEstado());
        model.setFechaInicio(request.getFechaInicio());
        model.setFechaFin(request.getFechaFin());
        model.setIdAgente(request.getIdAgente());
        model.setIdPropiedad(request.getIdPropiedad());

        return mapToResponse(mantencionRepository.save(model));
    }

    public void eliminarMantencion(Long id) {
        MantencionModel model = mantencionRepository.findById(id)
                .orElseThrow(() -> new MantencionNotFoundException(id));
        mantencionRepository.delete(model);
    }

    // para convertir mantencionmodel a mantencionmesponse
    private MantencionResponse mapToResponse(MantencionModel model) {
        return MantencionResponse.builder()
                .idMantencion(model.getIdMantencion())
                .descripcion(model.getDescripcion())
                .estado(model.getEstado())
                .fechaInicio(model.getFechaInicio())
                .fechaFin(model.getFechaFin())
                .agente(agenteClient.obtenerAgentePorId(model.getIdAgente()))
                .propiedad(propiedadClient.obtenerPropiedadPorId(model.getIdPropiedad()))
                .build();
    }
}
