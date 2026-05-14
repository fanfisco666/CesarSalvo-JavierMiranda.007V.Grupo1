package com.inmobiliaria.inmobiliaria_contrato.services;

import com.inmobiliaria.inmobiliaria_contrato.client.*;
import com.inmobiliaria.inmobiliaria_contrato.dtos.request.ContratoRequest;
import com.inmobiliaria.inmobiliaria_contrato.dtos.response.ContratoResponse;
import com.inmobiliaria.inmobiliaria_contrato.dtos.response.PropiedadResponse;
import com.inmobiliaria.inmobiliaria_contrato.dtos.response.VisitaResponse;
import com.inmobiliaria.inmobiliaria_contrato.exceptions.NotFoundException;
import com.inmobiliaria.inmobiliaria_contrato.exceptions.RemoteServiceException;
import com.inmobiliaria.inmobiliaria_contrato.models.ContratoModel;
import com.inmobiliaria.inmobiliaria_contrato.repositories.ContratoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ContratoService {

    private static final Logger log = LoggerFactory.getLogger(ContratoService.class);

    private final ContratoRepository contratoRepository;
    private final ClienteClient clienteClient;
    private final PropiedadClient propiedadClient;
    private final AgenteClient agenteClient;
    private final VisitaClient visitaClient;

    public ContratoService(ContratoRepository contratoRepository,
                           ClienteClient clienteClient,
                           PropiedadClient propiedadClient,
                           AgenteClient agenteClient,
                           VisitaClient visitaClient) {
        this.contratoRepository = contratoRepository;
        this.clienteClient = clienteClient;
        this.propiedadClient = propiedadClient;
        this.agenteClient = agenteClient;
        this.visitaClient = visitaClient;
    }

    public List<ContratoResponse> obtenerTodos() {
        log.info("Obteniendo lista de todos los contratos");
        List<ContratoModel> contratos = contratoRepository.findAll();
        log.info("Se encontraron {} contratos", contratos.size());
        return contratos.stream().map(this::toResponse).toList();
    }

    public ContratoResponse obtenerPorId(Long id) {
        log.info("Buscando contrato con ID: {}", id);
        ContratoModel contrato = contratoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Contrato con ID {} no encontrado", id);
                    return new NotFoundException("Contrato con ID " + id + " no encontrado");
                });
        return toResponse(contrato);
    }

    public List<ContratoResponse> obtenerPorCliente(Long idCliente) {
        log.info("Obteniendo contratos del cliente ID: {}", idCliente);
        return contratoRepository.findByIdCliente(idCliente)
                .stream().map(this::toResponse).toList();
    }

    public List<ContratoResponse> obtenerPorEstado(String estado) {
        log.info("Obteniendo contratos con estado: {}", estado);
        return contratoRepository.findByEstado(estado)
                .stream().map(this::toResponse).toList();
    }

    public ContratoResponse guardar(ContratoRequest dto) {
        log.info("Creando contrato tipo: {} para cliente ID: {}",
                dto.getTipoContrato(), dto.getIdCliente());

        // Regla 1 — validar cliente
        try {
            clienteClient.obtenerClientePorId(dto.getIdCliente());
            log.info("Cliente ID: {} validado", dto.getIdCliente());
        } catch (Exception e) {
            log.warn("Cliente ID: {} no encontrado", dto.getIdCliente());
            throw new RemoteServiceException("Cliente con ID " + dto.getIdCliente() + " no encontrado");
        }

        // Regla 2 — validar propiedad y obtener precio
        PropiedadResponse propiedad;
        try {
            propiedad = propiedadClient.obtenerPropiedadPorId(dto.getIdPropiedad());
            log.info("Propiedad ID: {} validada", dto.getIdPropiedad());
        } catch (Exception e) {
            log.warn("Propiedad ID: {} no encontrada", dto.getIdPropiedad());
            throw new RemoteServiceException("Propiedad con ID " + dto.getIdPropiedad() + " no encontrada");
        }

        // Regla 3 — validar agente
        try {
            agenteClient.obtenerAgentePorId(dto.getIdAgente());
            log.info("Agente ID: {} validado", dto.getIdAgente());
        } catch (Exception e) {
            log.warn("Agente ID: {} no encontrado", dto.getIdAgente());
            throw new RemoteServiceException("Agente con ID " + dto.getIdAgente() + " no encontrado");
        }

        // Regla 4 — validar que visita existe y está confirmada
        VisitaResponse visita;
        try {
            visita = visitaClient.obtenerVisitaPorId(dto.getIdVisita());
            log.info("Visita ID: {} validada con estado: {}", dto.getIdVisita(), visita.getEstado());
        } catch (Exception e) {
            log.warn("Visita ID: {} no encontrada", dto.getIdVisita());
            throw new RemoteServiceException("Visita con ID " + dto.getIdVisita() + " no encontrada");
        }

        if (!"CONFIRMADA".equals(visita.getEstado())) {
            log.warn("Visita ID: {} no está confirmada, estado actual: {}",
                    dto.getIdVisita(), visita.getEstado());
            throw new RemoteServiceException("La visita debe estar CONFIRMADA para crear un contrato");
        }

        // Regla 5 — propiedad no debe tener contrato vigente
        if (contratoRepository.existeContratoVigenteParaPropiedad(dto.getIdPropiedad())) {
            log.warn("Propiedad ID: {} ya tiene contrato vigente", dto.getIdPropiedad());
            throw new RemoteServiceException("La propiedad ya tiene un contrato vigente");
        }

        // Regla 6 — visita no debe tener contrato previo
        if (contratoRepository.existeContratoParaVisita(dto.getIdVisita())) {
            log.warn("Visita ID: {} ya tiene contrato asociado", dto.getIdVisita());
            throw new RemoteServiceException("La visita ya tiene un contrato asociado");
        }

        // Regla 7 — calcular monto según tipo
        BigDecimal montoTotal = calcularMonto(dto.getTipoContrato(), propiedad.getPrecio(), dto);

        ContratoModel modelo = new ContratoModel();
        modelo.setIdCliente(dto.getIdCliente());
        modelo.setIdPropiedad(dto.getIdPropiedad());
        modelo.setIdAgente(dto.getIdAgente());
        modelo.setIdVisita(dto.getIdVisita());
        modelo.setTipoContrato(dto.getTipoContrato());
        modelo.setMontoTotal(montoTotal);
        modelo.setFechaInicio(dto.getFechaInicio());
        modelo.setFechaFin(dto.getFechaFin());
        modelo.setEstado("VIGENTE");
        modelo.setObservaciones(dto.getObservaciones());

        ContratoModel contratoGuardado = contratoRepository.save(modelo);
        log.info("Contrato creado exitosamente con ID: {}, monto: {}",
                contratoGuardado.getIdContrato(), montoTotal);
        return toResponse(contratoGuardado);
    }

    public ContratoResponse cambiarEstado(Long id, String nuevoEstado) {
        log.info("Cambiando estado de contrato ID: {} a {}", id, nuevoEstado);
        ContratoModel contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contrato con ID " + id + " no encontrado"));
        contrato.setEstado(nuevoEstado);
        ContratoModel actualizado = contratoRepository.save(contrato);
        log.info("Estado de contrato ID: {} cambiado a {}", id, nuevoEstado);
        return toResponse(actualizado);
    }

    public void eliminar(Long id) {
        log.warn("Eliminando contrato con ID: {}", id);
        contratoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contrato con ID " + id + " no encontrado"));
        contratoRepository.deleteById(id);
        log.info("Contrato con ID {} eliminado", id);
    }

    // Regla de negocio — cálculo de monto
    private BigDecimal calcularMonto(String tipo, Double precioPropidad, ContratoRequest dto) {
        BigDecimal precio = BigDecimal.valueOf(precioPropidad);
        if ("ARRIENDO".equals(tipo)) {
            // Arriendo: precio mensual de la propiedad
            log.info("Calculando monto ARRIENDO: {}", precio);
            return precio;
        } else {
            // Venta: precio total de la propiedad + 2% comisión agente
            BigDecimal comision = precio.multiply(BigDecimal.valueOf(0.02));
            BigDecimal total = precio.add(comision);
            log.info("Calculando monto VENTA: {} + comisión: {} = {}", precio, comision, total);
            return total;
        }
    }

    private ContratoResponse toResponse(ContratoModel m) {
        return ContratoResponse.builder()
                .idContrato(m.getIdContrato())
                .idCliente(m.getIdCliente())
                .idPropiedad(m.getIdPropiedad())
                .idAgente(m.getIdAgente())
                .idVisita(m.getIdVisita())
                .tipoContrato(m.getTipoContrato())
                .montoTotal(m.getMontoTotal())
                .fechaInicio(m.getFechaInicio())
                .fechaFin(m.getFechaFin())
                .estado(m.getEstado())
                .observaciones(m.getObservaciones())
                .build();
    }
}