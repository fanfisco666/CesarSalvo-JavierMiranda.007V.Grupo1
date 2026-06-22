package com.inmobiliaria.inmobiliaria_mantencion.service;

import com.inmobiliaria.inmobiliaria_mantencion.client.AgenteClient;
import com.inmobiliaria.inmobiliaria_mantencion.client.PropiedadClient;
import com.inmobiliaria.inmobiliaria_mantencion.dtos.request.MantencionRequest;
import com.inmobiliaria.inmobiliaria_mantencion.dtos.response.MantencionResponse;
import com.inmobiliaria.inmobiliaria_mantencion.exceptions.NotFoundException;
import com.inmobiliaria.inmobiliaria_mantencion.model.MantencionModel;
import com.inmobiliaria.inmobiliaria_mantencion.repositories.MantencionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - MantencionService")
class MantencionServiceTest {

    // MOCKS
    @Mock
    private MantencionRepository mantencionRepository;

    @Mock
    private AgenteClient agenteClient;

    @Mock
    private PropiedadClient propiedadClient;

    // InjectMocks
    @InjectMocks
    private MantencionService mantencionService;

    // Objetos de prueba
    private MantencionModel mantencionModel;
    private MantencionRequest mantencionRequest;

    @BeforeEach
    void setUp() {
        mantencionModel = new MantencionModel();
        mantencionModel.setIdMantencion(1L);
        mantencionModel.setDescripcion("Reparación de techumbre");
        mantencionModel.setEstado("pendiente");
        mantencionModel.setFechaInicio(LocalDate.of(2026, 6, 1));
        mantencionModel.setFechaFin(null);
        mantencionModel.setIdAgente(10L);
        mantencionModel.setIdPropiedad(20L);

        mantencionRequest = new MantencionRequest();
        mantencionRequest.setDescripcion("Reparación de techumbre");
        mantencionRequest.setEstado("pendiente");
        mantencionRequest.setFechaInicio(LocalDate.of(2026, 6, 1));
        mantencionRequest.setFechaFin(null);
        mantencionRequest.setIdAgente(10L);
        mantencionRequest.setIdPropiedad(20L);

        // El servicio
    }

    @Test
    @DisplayName("obtenerTodasLasMantenciones - retorna lista con todas las mantenciones")
    void obtenerTodasLasMantenciones_RetornarListaMantenciones() {

        when(mantencionRepository.findAll()).thenReturn(List.of(mantencionModel));

        List<MantencionResponse> resultado = mantencionService.obtenerTodasLasMantenciones();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getDescripcion()).isEqualTo("Reparación de techumbre");
        verify(mantencionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("obtenerMantencionPorId - retorna mantención cuando existe")
    void obtenerMantencionPorId_RetornarMantencionExiste() {
        // Given
        when(mantencionRepository.findById(1L)).thenReturn(Optional.of(mantencionModel));

        // When
        MantencionResponse resultado = mantencionService.obtenerMantencionPorId(1L);

        // Then
        assertThat(resultado.getIdMantencion()).isEqualTo(1L);
        assertThat(resultado.getEstado()).isEqualTo("pendiente");
    }

    @Test
    @DisplayName("obtenerMantencionPorId - lanza NotFoundException cuando no existe")
    void obtenerMantencionPorId_LanzarExcepcionCuandoNoExiste() {

        when(mantencionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mantencionService.obtenerMantencionPorId(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("obtenerMantencionesPorEstado - filtra correctamente por estado")
    void obtenerMantencionesPorEstado_FiltrarPorEstado() {

        when(mantencionRepository.findByEstado("pendiente")).thenReturn(List.of(mantencionModel));

        List<MantencionResponse> resultado = mantencionService.obtenerMantencionesPorEstado("pendiente");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getEstado()).isEqualTo("pendiente");
        verify(mantencionRepository, times(1)).findByEstado("pendiente");
    }

    @Test
    @DisplayName("crearMantencion - persiste mantención y retorna response")
    void crearMantencion_PersistirMantencionYRetornarResponse() {

        when(mantencionRepository.save(any(MantencionModel.class))).thenReturn(mantencionModel);

        MantencionResponse resultado = mantencionService.crearMantencion(mantencionRequest);

        assertThat(resultado.getDescripcion()).isEqualTo("Reparación de techumbre");
        assertThat(resultado.getEstado()).isEqualTo("pendiente");
        verify(mantencionRepository, times(1)).save(any(MantencionModel.class));
    }

    @Test
    @DisplayName("actualizarMantencion - actualiza mantención existente y retorna response actualizado")
    void actualizarMantencion_ActualizarMantencionExistente() {

        MantencionRequest requestActualizado = new MantencionRequest();
        requestActualizado.setDescripcion("Reparación completa de techumbre");
        requestActualizado.setEstado("en progreso");
        requestActualizado.setFechaInicio(LocalDate.of(2026, 6, 1));
        requestActualizado.setFechaFin(null);
        requestActualizado.setIdAgente(10L);
        requestActualizado.setIdPropiedad(20L);

        MantencionModel mantencionActualizada = new MantencionModel();
        mantencionActualizada.setIdMantencion(1L);
        mantencionActualizada.setDescripcion("Reparación completa de techumbre");
        mantencionActualizada.setEstado("en progreso");
        mantencionActualizada.setFechaInicio(LocalDate.of(2026, 6, 1));
        mantencionActualizada.setIdAgente(10L);
        mantencionActualizada.setIdPropiedad(20L);

        when(mantencionRepository.findById(1L)).thenReturn(Optional.of(mantencionModel));
        when(mantencionRepository.save(any(MantencionModel.class))).thenReturn(mantencionActualizada);

        MantencionResponse resultado = mantencionService.actualizarMantencion(1L, requestActualizado);

        assertThat(resultado.getDescripcion()).isEqualTo("Reparación completa de techumbre");
        assertThat(resultado.getEstado()).isEqualTo("en progreso");
        verify(mantencionRepository, times(1)).findById(1L);
        verify(mantencionRepository, times(1)).save(any(MantencionModel.class));
    }

    @Test
    @DisplayName("actualizarMantencion - lanza NotFoundException cuando mantención no existe")
    void actualizarMantencion_LanzarExcepcionMantencionNoExiste() {

        when(mantencionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mantencionService.actualizarMantencion(99L, mantencionRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");

        verify(mantencionRepository, never()).save(any(MantencionModel.class));
    }

    @Test
    @DisplayName("eliminarMantencion - elimina mantención existente sin errores")
    void eliminarMantencion_EliminarMantencionExistente() {

        when(mantencionRepository.findById(1L)).thenReturn(Optional.of(mantencionModel));
        doNothing().when(mantencionRepository).delete(mantencionModel);

        mantencionService.eliminarMantencion(1L);

        verify(mantencionRepository, times(1)).delete(mantencionModel);
    }

    @Test
    @DisplayName("eliminarMantencion - lanza NotFoundException cuando mantención no existe")
    void eliminarMantencion_LanzarExcepcionMantencionNoExiste() {

        when(mantencionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mantencionService.eliminarMantencion(99L))
                .isInstanceOf(NotFoundException.class);

        verify(mantencionRepository, never()).delete(any(MantencionModel.class));
    }
}