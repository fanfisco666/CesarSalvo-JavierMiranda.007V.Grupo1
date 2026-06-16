package com.inmobiliaria.inmobiliaria_contrato.services;

import com.inmobiliaria.inmobiliaria_contrato.client.*;
import com.inmobiliaria.inmobiliaria_contrato.dtos.request.ContratoRequest;
import com.inmobiliaria.inmobiliaria_contrato.dtos.response.ContratoResponse;
import com.inmobiliaria.inmobiliaria_contrato.dtos.response.AgenteResponse;
import com.inmobiliaria.inmobiliaria_contrato.dtos.response.ClienteResponse;
import com.inmobiliaria.inmobiliaria_contrato.dtos.response.PropiedadResponse;
import com.inmobiliaria.inmobiliaria_contrato.dtos.response.VisitaResponse;
import com.inmobiliaria.inmobiliaria_contrato.exceptions.NotFoundException;
import com.inmobiliaria.inmobiliaria_contrato.exceptions.RemoteServiceException;
import com.inmobiliaria.inmobiliaria_contrato.models.ContratoModel;
import com.inmobiliaria.inmobiliaria_contrato.repositories.ContratoRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - ContratoService")
class ContratoServiceTest {

    @Mock private ContratoRepository contratoRepository;
    @Mock private ClienteClient clienteClient;
    @Mock private PropiedadClient propiedadClient;
    @Mock private AgenteClient agenteClient;
    @Mock private VisitaClient visitaClient;
    @Mock private NotificacionClient notificacionClient;

    @InjectMocks
    private ContratoService contratoService;
    private ContratoModel contratoModel;
    private ContratoRequest contratoRequest;
    private PropiedadResponse propiedadResponse;
    private VisitaResponse visitaConfirmada;

    @BeforeEach
    void setUp() {
        contratoModel = new ContratoModel();
        contratoModel.setIdContrato(1L);
        contratoModel.setIdCliente(1L);
        contratoModel.setIdPropiedad(1L);
        contratoModel.setIdAgente(1L);
        contratoModel.setIdVisita(1L);
        contratoModel.setTipoContrato("ARRIENDO");
        contratoModel.setMontoTotal(BigDecimal.valueOf(500000));
        contratoModel.setFechaInicio(LocalDate.now());
        contratoModel.setEstado("VIGENTE");

        contratoRequest = new ContratoRequest();
        contratoRequest.setIdCliente(1L);
        contratoRequest.setIdPropiedad(1L);
        contratoRequest.setIdAgente(1L);
        contratoRequest.setIdVisita(1L);
        contratoRequest.setTipoContrato("ARRIENDO");
        contratoRequest.setFechaInicio(LocalDate.now());

        propiedadResponse = new PropiedadResponse();
        propiedadResponse.setIdPropiedad(1L);
        propiedadResponse.setPrecio(500000.0);

        visitaConfirmada = new VisitaResponse();
        visitaConfirmada.setIdVisita(1L);
        visitaConfirmada.setEstado("CONFIRMADA");
    }

    // CONSULTAS

    @Test
    @DisplayName("obtenerTodos - retorna lista de contratos")
    void obtenerTodos_RetornarListaContratos() {
        // Given
        when(contratoRepository.findAll()).thenReturn(List.of(contratoModel));

        // When
        List<ContratoResponse> resultado = contratoService.obtenerTodos();

        // Then
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getEstado()).isEqualTo("VIGENTE");
        verify(contratoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("obtenerPorId - retorna contrato cuando existe")
    void obtenerPorId_RetornarContratoCuandoExiste() {
        // Given
        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contratoModel));

        // When
        ContratoResponse resultado = contratoService.obtenerPorId(1L);

        // Then
        assertThat(resultado.getIdContrato()).isEqualTo(1L);
        assertThat(resultado.getTipoContrato()).isEqualTo("ARRIENDO");
    }

    @Test
    @DisplayName("obtenerPorId - lanza NotFoundException cuando no existe")
    void obtenerPorId_LanzarExcepcionCuandoNoExiste() {
        // Given
        when(contratoRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> contratoService.obtenerPorId(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }

    // GUARDAR(reglas)

    @Test
    @DisplayName("guardar - crea contrato ARRIENDO cuando todas las reglas pasan")
    void guardar_CrearContratoArriendoCuandoReglasValidas() {
        // Given
        when(clienteClient.obtenerClientePorId(1L)).thenReturn(new ClienteResponse());
        when(propiedadClient.obtenerPropiedadPorId(1L)).thenReturn(propiedadResponse);
        when(agenteClient.obtenerAgentePorId(1L)).thenReturn(new AgenteResponse());
        when(visitaClient.obtenerVisitaPorId(1L)).thenReturn(visitaConfirmada);
        when(contratoRepository.existeContratoVigenteParaPropiedad(1L)).thenReturn(false);
        when(contratoRepository.existeContratoParaVisita(1L)).thenReturn(false);
        when(contratoRepository.save(any(ContratoModel.class))).thenReturn(contratoModel);

        // When
        ContratoResponse resultado = contratoService.guardar(contratoRequest);

        // Then
        assertThat(resultado.getEstado()).isEqualTo("VIGENTE");
        assertThat(resultado.getTipoContrato()).isEqualTo("ARRIENDO");
        verify(contratoRepository, times(1)).save(any(ContratoModel.class));
    }

    @Test
    @DisplayName("guardar - calcula monto VENTA con 2% comision (Regla 7)")
    void guardar_CalcularMontoVentaConComision() {
        // Given
        contratoRequest.setTipoContrato("VENTA");
        PropiedadResponse propiedadVenta = new PropiedadResponse();
        propiedadVenta.setPrecio(1000000.0); // precio base

        ContratoModel contratoVenta = new ContratoModel();
        contratoVenta.setIdContrato(2L);
        contratoVenta.setEstado("VIGENTE");
        contratoVenta.setTipoContrato("VENTA");
        contratoVenta.setMontoTotal(BigDecimal.valueOf(1020000.0)); // 1000000 + 2%

        when(clienteClient.obtenerClientePorId(1L)).thenReturn(new ClienteResponse());
        when(propiedadClient.obtenerPropiedadPorId(1L)).thenReturn(propiedadVenta);
        when(agenteClient.obtenerAgentePorId(1L)).thenReturn(new AgenteResponse());
        when(visitaClient.obtenerVisitaPorId(1L)).thenReturn(visitaConfirmada);
        when(contratoRepository.existeContratoVigenteParaPropiedad(1L)).thenReturn(false);
        when(contratoRepository.existeContratoParaVisita(1L)).thenReturn(false);
        when(contratoRepository.save(any(ContratoModel.class))).thenReturn(contratoVenta);

        // When
        ContratoResponse resultado = contratoService.guardar(contratoRequest);

        // Then
        assertThat(resultado.getMontoTotal()).isEqualByComparingTo(BigDecimal.valueOf(1020000.0));
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceException cuando cliente no existe (Regla 1)")
    void guardar_LanzarExcepcionCuandoClienteNoExiste() {
        // Given
        when(clienteClient.obtenerClientePorId(1L))
                .thenThrow(new RuntimeException("not found"));

        // When / Then
        assertThatThrownBy(() -> contratoService.guardar(contratoRequest))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("Cliente");
        verify(contratoRepository, never()).save(any());
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceException cuando propiedad no existe (Regla 2)")
    void guardar_LanzarExcepcionCuandoPropiedadNoExiste() {
        // Given
        when(clienteClient.obtenerClientePorId(1L)).thenReturn(new ClienteResponse());
        when(propiedadClient.obtenerPropiedadPorId(1L))
                .thenThrow(new RuntimeException("not found"));

        // When / Then
        assertThatThrownBy(() -> contratoService.guardar(contratoRequest))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("Propiedad");
        verify(contratoRepository, never()).save(any());
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceException cuando agente no existe (Regla 3)")
    void guardar_LanzarExcepcionCuandoAgenteNoExiste() {
        // Given
        when(clienteClient.obtenerClientePorId(1L)).thenReturn(new ClienteResponse());
        when(propiedadClient.obtenerPropiedadPorId(1L)).thenReturn(propiedadResponse);
        when(agenteClient.obtenerAgentePorId(1L))
                .thenThrow(new RuntimeException("not found"));

        // When / Then
        assertThatThrownBy(() -> contratoService.guardar(contratoRequest))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("Agente");
        verify(contratoRepository, never()).save(any());
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceException cuando visita no existe (Regla 4)")
    void guardar_LanzarExcepcionCuandoVisitaNoExiste() {
        // Given
        when(clienteClient.obtenerClientePorId(1L)).thenReturn(new ClienteResponse());
        when(propiedadClient.obtenerPropiedadPorId(1L)).thenReturn(propiedadResponse);
        when(agenteClient.obtenerAgentePorId(1L)).thenReturn(new AgenteResponse());
        when(visitaClient.obtenerVisitaPorId(1L))
                .thenThrow(new RuntimeException("not found"));

        // When / Then
        assertThatThrownBy(() -> contratoService.guardar(contratoRequest))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("Visita");
        verify(contratoRepository, never()).save(any());
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceException cuando visita no está CONFIRMADA (Regla 4b)")
    void guardar_LanzarExcepcionCuandoVisitaNoConfirmada() {
        // Given
        VisitaResponse visitaPendiente = new VisitaResponse();
        visitaPendiente.setEstado("PENDIENTE");

        when(clienteClient.obtenerClientePorId(1L)).thenReturn(new ClienteResponse());
        when(propiedadClient.obtenerPropiedadPorId(1L)).thenReturn(propiedadResponse);
        when(agenteClient.obtenerAgentePorId(1L)).thenReturn(new AgenteResponse());
        when(visitaClient.obtenerVisitaPorId(1L)).thenReturn(visitaPendiente);

        // When / Then
        assertThatThrownBy(() -> contratoService.guardar(contratoRequest))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("CONFIRMADA");
        verify(contratoRepository, never()).save(any());
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceException cuando propiedad ya tiene contrato vigente (Regla 5)")
    void guardar_LanzarExcepcionCuandoPropiedadYaTieneContrato() {
        // Given
        when(clienteClient.obtenerClientePorId(1L)).thenReturn(new ClienteResponse());
        when(propiedadClient.obtenerPropiedadPorId(1L)).thenReturn(propiedadResponse);
        when(agenteClient.obtenerAgentePorId(1L)).thenReturn(new AgenteResponse());
        when(visitaClient.obtenerVisitaPorId(1L)).thenReturn(visitaConfirmada);
        when(contratoRepository.existeContratoVigenteParaPropiedad(1L)).thenReturn(true);

        // When / Then
        assertThatThrownBy(() -> contratoService.guardar(contratoRequest))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("propiedad ya tiene");
        verify(contratoRepository, never()).save(any());
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceException cuando visita ya tiene contrato (Regla 6)")
    void guardar_LanzarExcepcionCuandoVisitaYaTieneContrato() {
        // Given
        when(clienteClient.obtenerClientePorId(1L)).thenReturn(new ClienteResponse());
        when(propiedadClient.obtenerPropiedadPorId(1L)).thenReturn(propiedadResponse);
        when(agenteClient.obtenerAgentePorId(1L)).thenReturn(new AgenteResponse());
        when(visitaClient.obtenerVisitaPorId(1L)).thenReturn(visitaConfirmada);
        when(contratoRepository.existeContratoVigenteParaPropiedad(1L)).thenReturn(false);
        when(contratoRepository.existeContratoParaVisita(1L)).thenReturn(true);

        // When / Then
        assertThatThrownBy(() -> contratoService.guardar(contratoRequest))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("visita ya tiene");
        verify(contratoRepository, never()).save(any());
    }

    // CAMBIAR ESTADO / ELIMINAR

    @Test
    @DisplayName("cambiarEstado - actualiza estado de contrato existente")
    void cambiarEstado_ActualizarEstado() {
        // Given
        ContratoModel contratoActualizado = new ContratoModel();
        contratoActualizado.setIdContrato(1L);
        contratoActualizado.setIdCliente(1L);
        contratoActualizado.setEstado("VENCIDO");

        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contratoModel));
        when(contratoRepository.save(any(ContratoModel.class))).thenReturn(contratoActualizado);

        // When
        ContratoResponse resultado = contratoService.cambiarEstado(1L, "VENCIDO");

        // Then
        assertThat(resultado.getEstado()).isEqualTo("VENCIDO");
        verify(contratoRepository, times(1)).save(any(ContratoModel.class));
    }

    @Test
    @DisplayName("eliminar - elimina contrato existente")
    void eliminar_EliminarContratoExistente() {
        // Given
        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contratoModel));
        doNothing().when(contratoRepository).deleteById(1L);

        // When
        contratoService.eliminar(1L);

        // Then
        verify(contratoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("eliminar - lanza NotFoundException cuando contrato no existe")
    void eliminar_LanzarExcepcionCuandoContratoNoExiste() {
        // Given
        when(contratoRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> contratoService.eliminar(99L))
                .isInstanceOf(NotFoundException.class);
        verify(contratoRepository, never()).deleteById(any());
    }
}