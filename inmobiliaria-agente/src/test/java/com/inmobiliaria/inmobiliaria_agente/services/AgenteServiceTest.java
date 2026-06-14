package com.inmobiliaria.inmobiliaria_agente.services;

import com.inmobiliaria.inmobiliaria_agente.dtos.request.AgenteRequest;
import com.inmobiliaria.inmobiliaria_agente.dtos.response.AgenteResponse;
import com.inmobiliaria.inmobiliaria_agente.exceptions.NotFoundExceptions;
import com.inmobiliaria.inmobiliaria_agente.models.AgenteModel;
import com.inmobiliaria.inmobiliaria_agente.repositories.AgenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - AgenteService")
class AgenteServiceTest {

    @Mock
    private AgenteRepository agenteRepository;

    @InjectMocks
    private AgenteService agenteService;

    private AgenteModel agenteModel;
    private AgenteRequest agenteRequest;

    @BeforeEach
    void setUp() {
        agenteModel = new AgenteModel();
        agenteModel.setIdAgente(1L);
        agenteModel.setRutAgente("11111111-1");
        agenteModel.setNombreAgente("Carlos");
        agenteModel.setApellidosAgente("González");
        agenteModel.setCorreoAgente("carlos@test.com");

        agenteRequest = new AgenteRequest();
        agenteRequest.setRutAgente("11111111-1");
        agenteRequest.setNombreAgente("Carlos");
        agenteRequest.setApellidosAgente("González");
        agenteRequest.setCorreoAgente("carlos@test.com");
    }

    @Test
    @DisplayName("obtenerTodos - retorna lista con todos los agentes")
    void obtenerTodos_deberiaRetornarListaAgentes() {
        // Given
        when(agenteRepository.findAll()).thenReturn(List.of(agenteModel));

        // When
        List<AgenteResponse> resultado = agenteService.obtenerTodos();

        // Then
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombreAgente()).isEqualTo("Carlos");
        verify(agenteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("obtenerPorId - retorna agente cuando existe")
    void obtenerPorId_deberiaRetornarAgenteCuandoExiste() {
        // Given
        when(agenteRepository.findById(1L)).thenReturn(Optional.of(agenteModel));

        // When
        AgenteResponse resultado = agenteService.obtenerPorId(1L);

        // Then
        assertThat(resultado.getIdAgente()).isEqualTo(1L);
        assertThat(resultado.getRutAgente()).isEqualTo("11111111-1");
    }

    @Test
    @DisplayName("obtenerPorId - lanza NotFoundExceptions cuando no existe")
    void obtenerPorId_deberiaLanzarExcepcionCuandoNoExiste() {
        // Given
        when(agenteRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> agenteService.obtenerPorId(99L))
                .isInstanceOf(NotFoundExceptions.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("guardar - persiste agente y retorna response")
    void guardar_deberiaPersistirAgenteYRetornarResponse() {
        // Given
        when(agenteRepository.save(any(AgenteModel.class))).thenReturn(agenteModel);

        // When
        AgenteResponse resultado = agenteService.guardar(agenteRequest);

        // Then
        assertThat(resultado.getNombreAgente()).isEqualTo("Carlos");
        assertThat(resultado.getCorreoAgente()).isEqualTo("carlos@test.com");
        verify(agenteRepository, times(1)).save(any(AgenteModel.class));
    }

    @Test
    @DisplayName("eliminar - elimina agente existente sin errores")
    void eliminar_deberiaEliminarAgenteExistente() {
        // Given
        when(agenteRepository.findById(1L)).thenReturn(Optional.of(agenteModel));
        doNothing().when(agenteRepository).delete(agenteModel);

        // When
        agenteService.eliminar(1L);

        // Then
        verify(agenteRepository, times(1)).delete(agenteModel);
    }

    @Test
    @DisplayName("eliminar - lanza NotFoundExceptions cuando agente no existe")
    void eliminar_deberiaLanzarExcepcionCuandoAgenteNoExiste() {
        // Given
        when(agenteRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> agenteService.eliminar(99L))
                .isInstanceOf(NotFoundExceptions.class);
    }
}
