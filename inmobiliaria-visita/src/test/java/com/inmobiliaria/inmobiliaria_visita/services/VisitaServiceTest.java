package com.inmobiliaria.inmobiliaria_visita.services;

import com.inmobiliaria.inmobiliaria_visita.client.AgenteClient;
import com.inmobiliaria.inmobiliaria_visita.client.ClienteClient;
import com.inmobiliaria.inmobiliaria_visita.client.PropiedadClient;
import com.inmobiliaria.inmobiliaria_visita.dtos.request.VisitaRequest;
import com.inmobiliaria.inmobiliaria_visita.dtos.response.AgenteResponse;
import com.inmobiliaria.inmobiliaria_visita.dtos.response.ClienteResponse;
import com.inmobiliaria.inmobiliaria_visita.dtos.response.PropiedadResponse;
import com.inmobiliaria.inmobiliaria_visita.dtos.response.VisitaResponse;
import com.inmobiliaria.inmobiliaria_visita.exceptions.NotFoundException;
import com.inmobiliaria.inmobiliaria_visita.exceptions.RemoteServiceException;
import com.inmobiliaria.inmobiliaria_visita.models.VisitaModel;
import com.inmobiliaria.inmobiliaria_visita.repositories.VisitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - VisitaService")
class VisitaServiceTest {

    @Mock
    private VisitaRepository visitaRepository;
    @Mock
    private ClienteClient clienteClient;
    @Mock
    private PropiedadClient propiedadClient;
    @Mock
    private AgenteClient agenteClient;

    @InjectMocks
    private VisitaService visitaService;
    private VisitaModel visitaModel;
    private VisitaRequest visitaRequest;

    @BeforeEach
    void setUp() {
        visitaModel = new VisitaModel();
        visitaModel.setIdVisita(1L);
        visitaModel.setIdCliente(1L);
        visitaModel.setIdPropiedad(1L);
        visitaModel.setIdAgente(1L);
        visitaModel.setFechaVisita(LocalDate.now().plusDays(5));
        visitaModel.setHoraVisita(LocalTime.of(10, 0));
        visitaModel.setEstado("PENDIENTE");
        visitaModel.setObservaciones("Test observacion");

        visitaRequest = new VisitaRequest();
        visitaRequest.setIdCliente(1L);
        visitaRequest.setIdPropiedad(1L);
        visitaRequest.setIdAgente(1L);
        visitaRequest.setFechaVisita(LocalDate.now().plusDays(5));
        visitaRequest.setHoraVisita(LocalTime.of(10, 0));
        visitaRequest.setObservaciones("Test observacion");
    }

    @Test
    @DisplayName("obtenerTodos - retorna lista de visitas")
    void obtenerTodos_RetornarListaVisitas() {
        // Given
        when(visitaRepository.findAll()).thenReturn(List.of(visitaModel));

        // When
        List<VisitaResponse> resultado = visitaService.obtenerTodos();

        // Then
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getEstado()).isEqualTo("PENDIENTE");
        verify(visitaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("obtenerPorId - retorna visita cuando existe")
    void obtenerPorId_RetornarVisitaCuandoExiste() {
        // Given
        when(visitaRepository.findById(1L)).thenReturn(Optional.of(visitaModel));

        // When
        VisitaResponse resultado = visitaService.obtenerPorId(1L);

        // Then
        assertThat(resultado.getIdVisita()).isEqualTo(1L);
        assertThat(resultado.getIdCliente()).isEqualTo(1L);
    }

    @Test
    @DisplayName("obtenerPorId - lanza NotFoundException cuando no existe")
    void obtenerPorId_LanzarExcepcionCuandoNoExiste() {
        // Given
        when(visitaRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> visitaService.obtenerPorId(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("guardar - agenda visita cuando todas las validaciones pasan")
    void guardar_AgendarVisitaCuandoValidacionesPasan() {
        // Given
        when(clienteClient.obtenerClientePorId(1L)).thenReturn(new ClienteResponse());
        when(propiedadClient.obtenerPropiedadPorId(1L)).thenReturn(new PropiedadResponse());
        when(agenteClient.obtenerAgentePorId(1L)).thenReturn(new AgenteResponse());
        when(visitaRepository.existeVisitaEnFecha(1L, visitaRequest.getFechaVisita()))
                .thenReturn(false);
        when(visitaRepository.save(any(VisitaModel.class))).thenReturn(visitaModel);

        // When
        VisitaResponse resultado = visitaService.guardar(visitaRequest);

        // Then
        assertThat(resultado.getEstado()).isEqualTo("PENDIENTE");
        assertThat(resultado.getIdCliente()).isEqualTo(1L);
        verify(visitaRepository, times(1)).save(any(VisitaModel.class));
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceException cuando cliente no existe (Regla 1)")
    void guardar_LanzarExcepcionCuandoClienteNoExiste() {
        // Given
        doThrow(new RuntimeException("not found"))
                .when(clienteClient).obtenerClientePorId(1L);

        // When / Then
        assertThatThrownBy(() -> visitaService.guardar(visitaRequest))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("Cliente");

        verify(visitaRepository, never()).save(any(VisitaModel.class));
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceException cuando propiedad no existe (Regla 2)")
    void guardar_LanzarExcepcionCuandoPropiedadNoExiste() {
        // Given
        when(clienteClient.obtenerClientePorId(1L)).thenReturn(new ClienteResponse());
        when(propiedadClient.obtenerPropiedadPorId(1L))
                .thenThrow(new RuntimeException("not found"));

        // When / Then
        assertThatThrownBy(() -> visitaService.guardar(visitaRequest))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("Propiedad");

        verify(visitaRepository, never()).save(any(VisitaModel.class));
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceException cuando agente no existe (Regla 3)")
    void guardar_LanzarExcepcionCuandoAgenteNoExiste() {
        // Given
        when(clienteClient.obtenerClientePorId(1L)).thenReturn(new ClienteResponse());
        when(propiedadClient.obtenerPropiedadPorId(1L)).thenReturn(new PropiedadResponse());
        when(agenteClient.obtenerAgentePorId(1L))
                .thenThrow(new RuntimeException("not found"));

        // When / Then
        assertThatThrownBy(() -> visitaService.guardar(visitaRequest))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("Agente");

        verify(visitaRepository, never()).save(any(VisitaModel.class));
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceException cuando ya existe visita en esa fecha (Regla 4)")
    void guardar_LanzarExcepcionCuandoYaExisteVisitaEnFecha() {
        // Given
        when(clienteClient.obtenerClientePorId(1L)).thenReturn(new ClienteResponse());
        when(propiedadClient.obtenerPropiedadPorId(1L)).thenReturn(new PropiedadResponse());
        when(agenteClient.obtenerAgentePorId(1L)).thenReturn(new AgenteResponse());
        lenient().when(visitaRepository.existeVisitaEnFecha(any(Long.class), any(LocalDate.class)))
                .thenReturn(true);

        // When / Then
        assertThatThrownBy(() -> visitaService.guardar(visitaRequest))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("Ya existe");

        verify(visitaRepository, never()).save(any(VisitaModel.class));
    }

    @Test
    @DisplayName("cambiarEstado - cambia estado de visita existente")
    void cambiarEstado_ActualizarEstadoDeVisita() {
        // Given
        VisitaModel visitaActualizada = new VisitaModel();
        visitaActualizada.setIdVisita(1L);
        visitaActualizada.setIdCliente(1L);
        visitaActualizada.setFechaVisita(visitaModel.getFechaVisita());
        visitaActualizada.setEstado("CONFIRMADA");

        when(visitaRepository.findById(1L)).thenReturn(Optional.of(visitaModel));
        when(visitaRepository.save(any(VisitaModel.class))).thenReturn(visitaActualizada);

        // When
        VisitaResponse resultado = visitaService.cambiarEstado(1L, "CONFIRMADA");

        // Then
        assertThat(resultado.getEstado()).isEqualTo("CONFIRMADA");
        verify(visitaRepository, times(1)).save(any(VisitaModel.class));
    }

    @Test
    @DisplayName("eliminar - elimina visita existente")
    void eliminar_EliminarVisitaExistente() {
        // Given
        when(visitaRepository.findById(1L)).thenReturn(Optional.of(visitaModel));
        doNothing().when(visitaRepository).deleteById(1L);

        // When
        visitaService.eliminar(1L);

        // Then
        verify(visitaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("eliminar - lanza NotFoundException cuando visita no existe")
    void eliminar_LanzarExcepcionCuandoVisitaNoExiste() {
        // Given
        when(visitaRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> visitaService.eliminar(99L))
                .isInstanceOf(NotFoundException.class);

        verify(visitaRepository, never()).deleteById(any());
    }
}