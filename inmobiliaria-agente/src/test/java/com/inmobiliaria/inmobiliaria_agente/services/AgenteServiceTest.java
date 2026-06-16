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
    void obtenerTodos_RetornarListaAgentes() {
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
    void obtenerPorId_RetornarAgenteExiste() {
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
    void obtenerPorId_LanzarExcepcionCuandoNoExiste() {
        // Given
        when(agenteRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> agenteService.obtenerPorId(99L))
                .isInstanceOf(NotFoundExceptions.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("guardar - persiste agente y retorna response")
    void guardar_PersistirAgenteYRetornarResponse() {
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
    void eliminar_EliminarAgenteExistente() {
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
    void eliminar_LanzarExcepcionAgenteNoExiste() {
        // Given
        when(agenteRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> agenteService.eliminar(99L))
                .isInstanceOf(NotFoundExceptions.class);
    }

    @Test
    @DisplayName("actualizar - actualiza agente existente y retorna response actualizado")
    void actualizar_ActualizarAgenteExistente() {
        // Given
        AgenteRequest requestActualizado = new AgenteRequest();
        requestActualizado.setRutAgente("22222222-2");
        requestActualizado.setNombreAgente("Carlos Actualizado");
        requestActualizado.setApellidosAgente("González Pérez");
        requestActualizado.setCorreoAgente("carlos.nuevo@test.com");

        AgenteModel agenteActualizado = new AgenteModel();
        agenteActualizado.setIdAgente(1L);
        agenteActualizado.setRutAgente("22222222-2");
        agenteActualizado.setNombreAgente("Carlos Actualizado");
        agenteActualizado.setApellidosAgente("González Pérez");
        agenteActualizado.setCorreoAgente("carlos.nuevo@test.com");

        when(agenteRepository.findById(1L)).thenReturn(Optional.of(agenteModel));
        when(agenteRepository.save(any(AgenteModel.class))).thenReturn(agenteActualizado);

        // When
        AgenteResponse resultado = agenteService.actualizar(1L, requestActualizado);

        // Then
        assertThat(resultado.getNombreAgente()).isEqualTo("Carlos Actualizado");
        assertThat(resultado.getRutAgente()).isEqualTo("22222222-2");
        assertThat(resultado.getCorreoAgente()).isEqualTo("carlos.nuevo@test.com");
        verify(agenteRepository, times(1)).findById(1L);
        verify(agenteRepository, times(1)).save(any(AgenteModel.class));
    }

    @Test
    @DisplayName("actualizar - lanza NotFoundExceptions cuando agente no existe")
    void actualizar_LanzarExcepcionAgenteNoExiste() {
        // Given
        when(agenteRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> agenteService.actualizar(99L, agenteRequest))
                .isInstanceOf(NotFoundExceptions.class)
                .hasMessageContaining("99");

        verify(agenteRepository, never()).save(any(AgenteModel.class));
    }
}
