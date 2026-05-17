package com.inmobiliaria.inmobiliaria_pago.services;

import com.inmobiliaria.inmobiliaria_pago.client.ClienteClient;
import com.inmobiliaria.inmobiliaria_pago.client.ContratoClient;
import com.inmobiliaria.inmobiliaria_pago.client.NotificacionClient;
import com.inmobiliaria.inmobiliaria_pago.dtos.request.NotificacionRequest;
import com.inmobiliaria.inmobiliaria_pago.dtos.request.PagoRequest;
import com.inmobiliaria.inmobiliaria_pago.dtos.response.ContratoResponse;
import com.inmobiliaria.inmobiliaria_pago.dtos.response.PagoResponse;
import com.inmobiliaria.inmobiliaria_pago.exceptions.NotFoundException;
import com.inmobiliaria.inmobiliaria_pago.exceptions.RemoteServiceException;
import com.inmobiliaria.inmobiliaria_pago.models.PagoModel;
import com.inmobiliaria.inmobiliaria_pago.repositories.PagoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PagoService {

    private static final Logger log = LoggerFactory.getLogger(PagoService.class);

    private final PagoRepository pagoRepository;
    private final ContratoClient contratoClient;
    private final ClienteClient clienteClient;
    private final NotificacionClient notificacionClient;

    public PagoService(PagoRepository pagoRepository,
                       ContratoClient contratoClient,
                       ClienteClient clienteClient,
                       NotificacionClient notificacionClient) {
        this.pagoRepository = pagoRepository;
        this.contratoClient = contratoClient;
        this.clienteClient = clienteClient;
        this.notificacionClient = notificacionClient;
    }
    public List<PagoResponse> obtenerTodos() {
        log.info("Obteniendo lista de todos los pagos");
        List<PagoModel> pagos = pagoRepository.findAll();
        log.info("Se encontraron {} pagos", pagos.size());
        return pagos.stream().map(this::toResponse).toList();
    }

    public PagoResponse obtenerPorId(Long id) {
        log.info("Buscando pago con ID: {}", id);
        PagoModel pago = pagoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pago con ID {} no encontrado", id);
                    return new NotFoundException("Pago con ID " + id + " no encontrado");
                });
        return toResponse(pago);
    }

    public List<PagoResponse> obtenerPorContrato(Long idContrato) {
        log.info("Obteniendo pagos del contrato ID: {}", idContrato);
        return pagoRepository.findByIdContrato(idContrato)
                .stream().map(this::toResponse).toList();
    }

    public List<PagoResponse> obtenerPorCliente(Long idCliente) {
        log.info("Obteniendo pagos del cliente ID: {}", idCliente);
        return pagoRepository.findByIdCliente(idCliente)
                .stream().map(this::toResponse).toList();
    }

    public List<PagoResponse> obtenerEnMora() {
        log.info("Obteniendo pagos en mora");
        List<PagoModel> mora = pagoRepository.findPagosEnMora(LocalDate.now());
        log.info("Se encontraron {} pagos en mora", mora.size());
        return mora.stream().map(this::toResponse).toList();
    }

    public PagoResponse guardar(PagoRequest dto) {
        log.info("Registrando pago para contrato ID: {}", dto.getIdContrato());

        // Regla 1 — validar que contrato existe y está vigente
        ContratoResponse contrato;
        try {
            contrato = contratoClient.obtenerContratoPorId(dto.getIdContrato());
            log.info("Contrato ID: {} validado con estado: {}", dto.getIdContrato(), contrato.getEstado());
        } catch (Exception e) {
            log.warn("Contrato ID: {} no encontrado", dto.getIdContrato());
            throw new RemoteServiceException("Contrato con ID " + dto.getIdContrato() + " no encontrado");
        }

        if (!"VIGENTE".equals(contrato.getEstado())) {
            log.warn("Contrato ID: {} no está vigente", dto.getIdContrato());
            throw new RemoteServiceException("El contrato no está vigente");
        }

        // Regla 2 — validar que cliente existe
        try {
            clienteClient.obtenerClientePorId(dto.getIdCliente());
            log.info("Cliente ID: {} validado", dto.getIdCliente());
        } catch (Exception e) {
            log.warn("Cliente ID: {} no encontrado", dto.getIdCliente());
            throw new RemoteServiceException("Cliente con ID " + dto.getIdCliente() + " no encontrado");
        }

        // Regla 3 — verificar que cuota no fue pagada
        if (dto.getNumeroCuota() != null &&
                pagoRepository.existeCuotaPagada(dto.getIdContrato(), dto.getNumeroCuota())) {
            log.warn("Cuota {} del contrato ID: {} ya fue pagada",
                    dto.getNumeroCuota(), dto.getIdContrato());
            throw new RemoteServiceException("La cuota " + dto.getNumeroCuota() + " ya fue pagada");
        }

        PagoModel modelo = new PagoModel();
        modelo.setIdContrato(dto.getIdContrato());
        modelo.setIdCliente(dto.getIdCliente());
        modelo.setMontoPago(dto.getMontoPago());
        modelo.setFechaPago(dto.getFechaPago());
        modelo.setFechaVencimiento(dto.getFechaVencimiento());
        modelo.setMetodoPago(dto.getMetodoPago());
        modelo.setEstado("PENDIENTE");
        modelo.setNumeroCuota(dto.getNumeroCuota());
        modelo.setObservaciones(dto.getObservaciones());

        PagoModel pagoGuardado = pagoRepository.save(modelo);
        log.info("Pago registrado exitosamente con ID: {}", pagoGuardado.getIdPago());
        try {
            notificacionClient.enviarNotificacion(new NotificacionRequest(
                    pagoGuardado.getIdCliente(),
                    "PAGO_PENDIENTE",
                    "Tiene un pago pendiente de $" + pagoGuardado.getMontoPago() +
                            " con vencimiento el " + pagoGuardado.getFechaVencimiento(),
                    "PAGO-" + pagoGuardado.getIdPago()
            ));
            log.info("Notificacion enviada para pago ID: {}", pagoGuardado.getIdPago());
        } catch (Exception e) {
            log.warn("No se pudo enviar notificacion para pago ID: {}", pagoGuardado.getIdPago());
        }

        return toResponse(pagoGuardado);
    }

    public PagoResponse confirmarPago(Long id) {
        log.info("Confirmando pago con ID: {}", id);
        PagoModel pago = pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pago con ID " + id + " no encontrado"));

        // Regla 4 — no pagar un pago ya pagado
        if ("PAGADO".equals(pago.getEstado())) {
            log.warn("Pago ID: {} ya fue confirmado", id);
            throw new RemoteServiceException("El pago ya fue confirmado anteriormente");
        }

        pago.setEstado("PAGADO");
        PagoModel pagoActualizado = pagoRepository.save(pago);
        log.info("Pago ID: {} confirmado exitosamente", id);
        try {
            notificacionClient.enviarNotificacion(new NotificacionRequest(
                    pagoActualizado.getIdCliente(),
                    "PAGO_CONFIRMADO",
                    "Su pago de $" + pagoActualizado.getMontoPago() + " ha sido confirmado exitosamente",
                    "PAGO-" + pagoActualizado.getIdPago()
            ));
            log.info("Notificacion de pago confirmado enviada para pago ID: {}", id);
        } catch (Exception e) {
            log.warn("No se pudo enviar notificacion de confirmacion para pago ID: {}", id);
        }
        return toResponse(pagoActualizado);
    }

    public void eliminar(Long id) {
        log.warn("Eliminando pago con ID: {}", id);
        pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pago con ID " + id + " no encontrado"));
        pagoRepository.deleteById(id);
        log.info("Pago con ID {} eliminado", id);
    }

    private PagoResponse toResponse(PagoModel m) {
        boolean enMora = "PENDIENTE".equals(m.getEstado()) &&
                m.getFechaVencimiento().isBefore(LocalDate.now());
        return PagoResponse.builder()
                .idPago(m.getIdPago())
                .idContrato(m.getIdContrato())
                .idCliente(m.getIdCliente())
                .montoPago(m.getMontoPago())
                .fechaPago(m.getFechaPago())
                .fechaVencimiento(m.getFechaVencimiento())
                .metodoPago(m.getMetodoPago())
                .estado(m.getEstado())
                .numeroCuota(m.getNumeroCuota())
                .observaciones(m.getObservaciones())
                .enMora(enMora)
                .build();
    }
}