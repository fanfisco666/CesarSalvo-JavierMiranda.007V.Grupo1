package com.inmobiliaria.inmobiliaria_reporte.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inmobiliaria.inmobiliaria_reporte.client.AgenteClient;
import com.inmobiliaria.inmobiliaria_reporte.client.ClienteClient;
import com.inmobiliaria.inmobiliaria_reporte.client.PropiedadClient;
import com.inmobiliaria.inmobiliaria_reporte.dtos.response.AgenteResponse;
import com.inmobiliaria.inmobiliaria_reporte.dtos.response.ClienteResponse;
import com.inmobiliaria.inmobiliaria_reporte.dtos.response.PropiedadResponse;
import com.inmobiliaria.inmobiliaria_reporte.dtos.request.ReporteRequest;
import com.inmobiliaria.inmobiliaria_reporte.dtos.response.ReporteResponse;
import com.inmobiliaria.inmobiliaria_reporte.models.ReporteModel;
import com.inmobiliaria.inmobiliaria_reporte.repositories.ReporteRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final PropiedadClient propiedadClient;
    private final AgenteClient agenteClient;
    private final ClienteClient clienteClient;

    public ReporteService(
            ReporteRepository reporteRepository,
            PropiedadClient propiedadClient,
            AgenteClient agenteClient,
            ClienteClient clienteClient) {

        this.reporteRepository = reporteRepository;
        this.propiedadClient = propiedadClient;
        this.agenteClient = agenteClient;
        this.clienteClient = clienteClient;
    }

    // para listarlos todos
    public List<ReporteResponse> obtenerReportes() {

        log.info("Obteniendo lista de reportes");

        List<ReporteModel> reportes = reporteRepository.findAll();
        return reportes.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // rara guardarlos
    public ReporteResponse guardar(ReporteRequest dto) {
        log.info("Guardando nuevo reporte");
        propiedadClient.obtenerPropiedadPorId(dto.getIdPropiedad());
        agenteClient.obtenerAgentePorId(dto.getIdAgente());
        clienteClient.obtenerClientePorId(dto.getIdUsuario());

        // para crear el modelo
        ReporteModel modelo = new ReporteModel();

        modelo.setTitulo(dto.getTitulo());
        modelo.setDescripcion(dto.getDescripcion());
        modelo.setIdPropiedad(dto.getIdPropiedad());
        modelo.setIdUsuario(dto.getIdUsuario());
        modelo.setIdAgente(dto.getIdAgente());
        modelo.setTipoReporte(dto.getTipoReporte());

        ReporteModel reporteGuardado = reporteRepository.save(modelo);

        return mapToResponse(reporteGuardado);
    }

    // para actualizar
    public ReporteResponse actualizar(Long id, ReporteRequest dto) {

        log.info("Actualizando reporte con ID: {}", id);

        ReporteModel modelo = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con ID: " + id));

        propiedadClient.obtenerPropiedadPorId(dto.getIdPropiedad());
        agenteClient.obtenerAgentePorId(dto.getIdAgente());
        clienteClient.obtenerClientePorId(dto.getIdUsuario());

        // para actualizar los datos
        modelo.setTitulo(dto.getTitulo());
        modelo.setDescripcion(dto.getDescripcion());
        modelo.setIdPropiedad(dto.getIdPropiedad());
        modelo.setIdUsuario(dto.getIdUsuario());
        modelo.setIdAgente(dto.getIdAgente());
        modelo.setTipoReporte(dto.getTipoReporte());

        ReporteModel reporteActualizado = reporteRepository.save(modelo);

        return mapToResponse(reporteActualizado);
    }

    // para eliminar
    public void eliminar(@NotNull Long id) {

        log.info("Eliminando reporte con ID: {}", id);

        ReporteModel reporte = reporteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Reporte no encontrado con ID: {}", id);
                    return new RuntimeException("Reporte no encontrado con ID: " + id);
                });

        reporteRepository.delete(reporte);
    }

    // optencion por id
    public ReporteResponse obtenerPorId(Long id) {

        log.info("Obteniendo reporte con ID: {}", id);

        ReporteModel reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con ID: " + id));

        return mapToResponse(reporte);
    }

    // para mapear
    private ReporteResponse mapToResponse(ReporteModel reporte) {

        // para consumir el microservicio
        PropiedadResponse propiedad = propiedadClient.obtenerPropiedadPorId(
                reporte.getIdPropiedad());

        ClienteResponse cliente = clienteClient.obtenerClientePorId(
                reporte.getIdUsuario());

        AgenteResponse agente = agenteClient.obtenerAgentePorId(
                reporte.getIdAgente());

        // construccion del response
        return ReporteResponse.builder()
                .idReporte(reporte.getIdReporte())
                .titulo(reporte.getTitulo())
                .descripcion(reporte.getDescripcion())
                .tipoReporte(reporte.getTipoReporte())

                .propiedad(propiedad)
                .cliente(cliente)
                .agente(agente)

                .build();
    }
}