package com.inmobiliaria.inmobiliaria_pago.services;

import com.inmobiliaria.inmobiliaria_pago.client.ClienteClient;
import com.inmobiliaria.inmobiliaria_pago.client.ContratoClient;
import com.inmobiliaria.inmobiliaria_pago.client.NotificacionClient;
import com.inmobiliaria.inmobiliaria_pago.dtos.request.PagoRequest;
import com.inmobiliaria.inmobiliaria_pago.dtos.response.ContratoResponse;
import com.inmobiliaria.inmobiliaria_pago.dtos.response.PagoResponse;
import com.inmobiliaria.inmobiliaria_pago.exceptions.NotFoundException;
import com.inmobiliaria.inmobiliaria_pago.exceptions.RemoteServiceException;
import com.inmobiliaria.inmobiliaria_pago.models.PagoModel;
import com.inmobiliaria.inmobiliaria_pago.repositories.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - PagoService")
class PagoServiceTest {

    // 4 mocks: el repositorio + los 3 Feign Clients que el service consulta
    // (Contrato, Cliente y Notificacion).
    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private ContratoClient contratoClient;

    @Mock
    private ClienteClient clienteClient;

    @Mock
    private NotificacionClient notificacionClient;

    @InjectMocks
    private PagoService pagoService;

    private PagoModel pagoModel;
    private PagoRequest pagoRequest;
    private ContratoResponse contratoVigente;

    @BeforeEach
    void setUp() {
        pagoModel = new PagoModel();
        pagoModel.setIdPago(1L);
        pagoModel.setIdContrato(10L);
        pagoModel.setIdCliente(20L);
        pagoModel.setMontoPago(new BigDecimal("350000"));
        pagoModel.setFechaPago(LocalDate.of(2026, 6, 1));
        pagoModel.setFechaVencimiento(LocalDate.of(2026, 12, 1)); // futuro: no está en mora
        pagoModel.setMetodoPago("TRANSFERENCIA");
        pagoModel.setEstado("PENDIENTE");
        pagoModel.setNumeroCuota(1);
        pagoModel.setObservaciones("Primera cuota");

        pagoRequest = new PagoRequest();
        pagoRequest.setIdContrato(10L);
        pagoRequest.setIdCliente(20L);
        pagoRequest.setMontoPago(new BigDecimal("350000"));
        pagoRequest.setFechaPago(LocalDate.of(2026, 6, 1));
        pagoRequest.setFechaVencimiento(LocalDate.of(2026, 12, 1));
        pagoRequest.setMetodoPago("TRANSFERENCIA");
        pagoRequest.setNumeroCuota(1);
        pagoRequest.setObservaciones("Primera cuota");

        // Contrato "feliz" que cumple la regla de negocio: debe estar VIGENTE
        contratoVigente = new ContratoResponse();
        contratoVigente.setIdContrato(10L);
        contratoVigente.setIdCliente(20L);
        contratoVigente.setEstado("VIGENTE");
    }

    @Test
    @DisplayName("obtenerTodos - retorna lista con todos los pagos")
    void obtenerTodos_RetornarListaPagos() {
        // Given
        when(pagoRepository.findAll()).thenReturn(List.of(pagoModel));

        // When
        List<PagoResponse> resultado = pagoService.obtenerTodos();

        // Then
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getMontoPago()).isEqualByComparingTo("350000");
        verify(pagoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("obtenerPorId - retorna pago cuando existe")
    void obtenerPorId_RetornarPagoExiste() {
        // Given
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoModel));

        // When
        PagoResponse resultado = pagoService.obtenerPorId(1L);

        // Then
        assertThat(resultado.getIdPago()).isEqualTo(1L);
        assertThat(resultado.getEstado()).isEqualTo("PENDIENTE");
    }

    @Test
    @DisplayName("obtenerPorId - lanza NotFoundException cuando no existe")
    void obtenerPorId_LanzarExcepcionCuandoNoExiste() {
        // Given
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> pagoService.obtenerPorId(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("obtenerPorId - marca enMora=true cuando vencimiento pasó y sigue pendiente")
    void obtenerPorId_MarcarEnMoraCuandoVencido() {
        // Given: un pago vencido en el pasado y todavía pendiente
        PagoModel pagoVencido = new PagoModel();
        pagoVencido.setIdPago(2L);
        pagoVencido.setIdContrato(10L);
        pagoVencido.setIdCliente(20L);
        pagoVencido.setMontoPago(new BigDecimal("100000"));
        pagoVencido.setFechaPago(LocalDate.of(2026, 1, 1));
        pagoVencido.setFechaVencimiento(LocalDate.of(2026, 1, 15)); // ya pasó
        pagoVencido.setMetodoPago("EFECTIVO");
        pagoVencido.setEstado("PENDIENTE");

        when(pagoRepository.findById(2L)).thenReturn(Optional.of(pagoVencido));

        // When
        PagoResponse resultado = pagoService.obtenerPorId(2L);

        // Then: la regla de negocio del mapeo debe marcar enMora=true
        assertThat(resultado.getEnMora()).isTrue();
    }

    @Test
    @DisplayName("obtenerPorContrato - retorna pagos de un contrato específico")
    void obtenerPorContrato_RetornarPagosDelContrato() {
        // Given
        when(pagoRepository.findByIdContrato(10L)).thenReturn(List.of(pagoModel));

        // When
        List<PagoResponse> resultado = pagoService.obtenerPorContrato(10L);

        // Then
        assertThat(resultado).hasSize(1);
        verify(pagoRepository, times(1)).findByIdContrato(10L);
    }

    @Test
    @DisplayName("obtenerEnMora - retorna solo los pagos vencidos y pendientes")
    void obtenerEnMora_RetornarPagosVencidos() {
        // Given
        when(pagoRepository.findPagosEnMora(any(LocalDate.class))).thenReturn(List.of(pagoModel));

        // When
        List<PagoResponse> resultado = pagoService.obtenerEnMora();

        // Then
        assertThat(resultado).hasSize(1);
        verify(pagoRepository, times(1)).findPagosEnMora(any(LocalDate.class));
    }

    @Test
    @DisplayName("guardar - persiste pago cuando contrato vigente, cliente existe y cuota libre")
    void guardar_PersistirPagoCuandoTodoValido() {
        // Given: las 3 reglas de negocio se cumplen
        when(contratoClient.obtenerContratoPorId(10L)).thenReturn(contratoVigente);
        when(clienteClient.obtenerClientePorId(20L)).thenReturn(null); // solo nos interesa que no lance excepción
        when(pagoRepository.existeCuotaPagada(10L, 1)).thenReturn(false);
        when(pagoRepository.save(any(PagoModel.class))).thenReturn(pagoModel);

        // When
        PagoResponse resultado = pagoService.guardar(pagoRequest);

        // Then
        assertThat(resultado.getEstado()).isEqualTo("PENDIENTE");
        assertThat(resultado.getMontoPago()).isEqualByComparingTo("350000");
        verify(pagoRepository, times(1)).save(any(PagoModel.class));
        // La notificación es "best effort": verificamos que se intentó enviar
        verify(notificacionClient, times(1)).enviarNotificacion(any());
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceException cuando el contrato no existe")
    void guardar_LanzarExcepcionCuandoContratoNoExiste() {
        // Given: el Feign Client lanza una excepción al no encontrar el contrato
        when(contratoClient.obtenerContratoPorId(10L)).thenThrow(new RuntimeException("404"));

        // When / Then
        assertThatThrownBy(() -> pagoService.guardar(pagoRequest))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("no encontrado");

        // Como falló validando el contrato, jamás debió guardar nada
        verify(pagoRepository, never()).save(any(PagoModel.class));
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceException cuando el contrato no está vigente")
    void guardar_LanzarExcepcionCuandoContratoNoVigente() {
        // Given: el contrato existe, pero su estado no es VIGENTE
        ContratoResponse contratoFinalizado = new ContratoResponse();
        contratoFinalizado.setIdContrato(10L);
        contratoFinalizado.setEstado("FINALIZADO");

        when(contratoClient.obtenerContratoPorId(10L)).thenReturn(contratoFinalizado);

        // When / Then
        assertThatThrownBy(() -> pagoService.guardar(pagoRequest))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("no está vigente");

        verify(pagoRepository, never()).save(any(PagoModel.class));
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceException cuando la cuota ya fue pagada")
    void guardar_LanzarExcepcionCuandoCuotaYaPagada() {
        // Given: contrato vigente y cliente válido, pero la cuota 1 ya está pagada
        when(contratoClient.obtenerContratoPorId(10L)).thenReturn(contratoVigente);
        when(clienteClient.obtenerClientePorId(20L)).thenReturn(null);
        when(pagoRepository.existeCuotaPagada(10L, 1)).thenReturn(true);

        // When / Then
        assertThatThrownBy(() -> pagoService.guardar(pagoRequest))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("ya fue pagada");

        verify(pagoRepository, never()).save(any(PagoModel.class));
    }

    @Test
    @DisplayName("confirmarPago - cambia estado a PAGADO cuando estaba pendiente")
    void confirmarPago_CambiarEstadoAPagado() {
        // Given
        PagoModel pagoPagado = new PagoModel();
        pagoPagado.setIdPago(1L);
        pagoPagado.setIdContrato(10L);
        pagoPagado.setIdCliente(20L);
        pagoPagado.setMontoPago(pagoModel.getMontoPago());
        pagoPagado.setFechaPago(pagoModel.getFechaPago());
        pagoPagado.setFechaVencimiento(pagoModel.getFechaVencimiento());
        pagoPagado.setMetodoPago("TRANSFERENCIA");
        pagoPagado.setEstado("PAGADO");

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoModel));
        when(pagoRepository.save(any(PagoModel.class))).thenReturn(pagoPagado);

        // When
        PagoResponse resultado = pagoService.confirmarPago(1L);

        // Then
        assertThat(resultado.getEstado()).isEqualTo("PAGADO");
        verify(pagoRepository, times(1)).save(any(PagoModel.class));
        verify(notificacionClient, times(1)).enviarNotificacion(any());
    }

    @Test
    @DisplayName("confirmarPago - lanza RemoteServiceException si el pago ya estaba pagado")
    void confirmarPago_LanzarExcepcionSiYaEstabaPagado() {
        // Given: el pago en la base de datos ya tiene estado PAGADO
        PagoModel pagoYaPagado = new PagoModel();
        pagoYaPagado.setIdPago(1L);
        pagoYaPagado.setEstado("PAGADO");

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoYaPagado));

        // When / Then
        assertThatThrownBy(() -> pagoService.confirmarPago(1L))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("ya fue confirmado");

        verify(pagoRepository, never()).save(any(PagoModel.class));
    }

    @Test
    @DisplayName("confirmarPago - lanza NotFoundException cuando pago no existe")
    void confirmarPago_LanzarExcepcionCuandoPagoNoExiste() {
        // Given
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> pagoService.confirmarPago(99L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("eliminar - elimina pago existente sin errores")
    void eliminar_EliminarPagoExistente() {
        // Given
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoModel));
        doNothing().when(pagoRepository).deleteById(1L);

        // When
        pagoService.eliminar(1L);

        // Then
        verify(pagoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("eliminar - lanza NotFoundException cuando pago no existe")
    void eliminar_LanzarExcepcionPagoNoExiste() {
        // Given
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> pagoService.eliminar(99L))
                .isInstanceOf(NotFoundException.class);

        verify(pagoRepository, never()).deleteById(anyLong());
    }
}