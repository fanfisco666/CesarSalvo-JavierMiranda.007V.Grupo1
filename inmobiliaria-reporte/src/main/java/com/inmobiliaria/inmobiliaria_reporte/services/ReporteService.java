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
import com.inmobiliaria.inmobiliaria_reporte.exceptions.NotFoundExceptions;
import com.inmobiliaria.inmobiliaria_reporte.exceptions.RemoteServiceExceptions;
import com.inmobiliaria.inmobiliaria_reporte.models.ReporteModel;
import com.inmobiliaria.inmobiliaria_reporte.repositories.ReporteRepository;

import jakarta.transaction.Transactional;
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

    public List<ReporteResponse> obtenerReportes() {
        log.info("Obteniendo lista de reportes");
        List<ReporteModel> reportes = reporteRepository.findAll();
        log.info("Se encontraron {} reportes", reportes.size());
        return reportes.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ReporteResponse guardar(ReporteRequest dto) {
        log.info("Guardando nuevo reporte titulo: {}", dto.getTitulo());

        // Validar que los recursos externos existen antes de guardar
        try {
            propiedadClient.obtenerPropiedadPorId(dto.getIdPropiedad());
        } catch (Exception e) {
            log.warn("Propiedad ID: {} no encontrada", dto.getIdPropiedad());
            throw new RemoteServiceExceptions("Propiedad con ID " + dto.getIdPropiedad() + " no encontrada");
        }

        try {
            agenteClient.obtenerAgentePorId(dto.getIdAgente());
        } catch (Exception e) {
            log.warn("Agente ID: {} no encontrado", dto.getIdAgente());
            throw new RemoteServiceExceptions("Agente con ID " + dto.getIdAgente() + " no encontrado");
        }

        try {
            clienteClient.obtenerClientePorId(dto.getIdUsuario());
        } catch (Exception e) {
            log.warn("Cliente (usuario) ID: {} no encontrado", dto.getIdUsuario());
            throw new RemoteServiceExceptions("Cliente con ID " + dto.getIdUsuario() + " no encontrado");
        }

        ReporteModel modelo = new ReporteModel();
        modelo.setTitulo(dto.getTitulo());
        modelo.setDescripcion(dto.getDescripcion());
        modelo.setIdPropiedad(dto.getIdPropiedad());
        modelo.setIdUsuario(dto.getIdUsuario());
        modelo.setIdAgente(dto.getIdAgente());
        modelo.setTipoReporte(dto.getTipoReporte());

        ReporteModel reporteGuardado = reporteRepository.save(modelo);
        log.info("Reporte guardado con ID: {}", reporteGuardado.getIdReporte());
        return mapToResponse(reporteGuardado);
    }

    public ReporteResponse actualizar(Long id, ReporteRequest dto) {
        log.info("Actualizando reporte con ID: {}", id);

        ReporteModel modelo = reporteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Reporte no encontrado con ID: {}", id);
                    return new NotFoundExceptions("Reporte no encontrado con ID: " + id);
                });

        try {
            propiedadClient.obtenerPropiedadPorId(dto.getIdPropiedad());
        } catch (Exception e) {
            throw new RemoteServiceExceptions("Propiedad con ID " + dto.getIdPropiedad() + " no encontrada");
        }

        try {
            agenteClient.obtenerAgentePorId(dto.getIdAgente());
        } catch (Exception e) {
            throw new RemoteServiceExceptions("Agente con ID " + dto.getIdAgente() + " no encontrado");
        }

        try {
            clienteClient.obtenerClientePorId(dto.getIdUsuario());
        } catch (Exception e) {
            throw new RemoteServiceExceptions("Cliente con ID " + dto.getIdUsuario() + " no encontrado");
        }

        modelo.setTitulo(dto.getTitulo());
        modelo.setDescripcion(dto.getDescripcion());
        modelo.setIdPropiedad(dto.getIdPropiedad());
        modelo.setIdUsuario(dto.getIdUsuario());
        modelo.setIdAgente(dto.getIdAgente());
        modelo.setTipoReporte(dto.getTipoReporte());

        ReporteModel reporteActualizado = reporteRepository.save(modelo);
        log.info("Reporte ID: {} actualizado", id);
        return mapToResponse(reporteActualizado);
    }

    public void eliminar(Long id) {
        log.info("Eliminando reporte con ID: {}", id);
        ReporteModel reporte = reporteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Reporte no encontrado con ID: {}", id);
                    return new NotFoundExceptions("Reporte no encontrado con ID: " + id);
                });
        reporteRepository.delete(reporte);
        log.info("Reporte ID: {} eliminado", id);
    }

    public ReporteResponse obtenerPorId(Long id) {
        log.info("Obteniendo reporte con ID: {}", id);
        ReporteModel reporte = reporteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Reporte no encontrado con ID: {}", id);
                    return new NotFoundExceptions("Reporte no encontrado con ID: " + id);
                });
        return mapToResponse(reporte);
    }

    // --- mapToResponse con try-catch por cada llamada Feign ---
    // Si un servicio externo falla o el ID no existe, retorna null en ese campo
    // en lugar de explotar con 500
    private ReporteResponse mapToResponse(ReporteModel reporte) {

        PropiedadResponse propiedad = null;
        try {
            propiedad = propiedadClient.obtenerPropiedadPorId(reporte.getIdPropiedad());
        } catch (Exception e) {
            log.warn("No se pudo obtener propiedad ID: {} para reporte ID: {}",
                    reporte.getIdPropiedad(), reporte.getIdReporte());
        }

        ClienteResponse cliente = null;
        try {
            cliente = clienteClient.obtenerClientePorId(reporte.getIdUsuario());
        } catch (Exception e) {
            log.warn("No se pudo obtener cliente (usuario) ID: {} para reporte ID: {}",
                    reporte.getIdUsuario(), reporte.getIdReporte());
        }

        AgenteResponse agente = null;
        try {
            agente = agenteClient.obtenerAgentePorId(reporte.getIdAgente());
        } catch (Exception e) {
            log.warn("No se pudo obtener agente ID: {} para reporte ID: {}",
                    reporte.getIdAgente(), reporte.getIdReporte());
        }

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
