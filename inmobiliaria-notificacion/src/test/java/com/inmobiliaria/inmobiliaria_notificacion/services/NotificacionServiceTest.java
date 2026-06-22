package com.inmobiliaria.inmobiliaria_notificacion.services;

import com.inmobiliaria.inmobiliaria_notificacion.dtos.request.NotificacionRequest;
import com.inmobiliaria.inmobiliaria_notificacion.dtos.response.NotificacionResponse;
import com.inmobiliaria.inmobiliaria_notificacion.exceptions.NotFoundException;
import com.inmobiliaria.inmobiliaria_notificacion.models.NotificacionModel;
import com.inmobiliaria.inmobiliaria_notificacion.repositories.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - NotificacionService")
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    private NotificacionModel notificacionModel;
    private NotificacionRequest notificacionRequest;

    @BeforeEach
    void setUp() {
        notificacionModel = new NotificacionModel();
        notificacionModel.setIdNotificacion(1L);
        notificacionModel.setIdCliente(50L);
        notificacionModel.setTipo("VISITA_AGENDADA");
        notificacionModel.setMensaje("Tu visita fue agendada para el 25 de junio");
        notificacionModel.setFechaEnvio(LocalDateTime.of(2026, 6, 20, 10, 0));
        notificacionModel.setLeida(false);
        notificacionModel.setReferencia("VISITA-100");

        notificacionRequest = new NotificacionRequest();
        notificacionRequest.setIdCliente(50L);
        notificacionRequest.setTipo("VISITA_AGENDADA");
        notificacionRequest.setMensaje("Tu visita fue agendada para el 25 de junio");
        notificacionRequest.setReferencia("VISITA-100");
    }

    @Test
    @DisplayName("obtenerTodos - retorna lista con todas las notificaciones")
    void obtenerTodos_RetornarListaNotificaciones() {
        // Given
        when(notificacionRepository.findAll()).thenReturn(List.of(notificacionModel));

        // When
        List<NotificacionResponse> resultado = notificacionService.obtenerTodos();

        // Then
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getTipo()).isEqualTo("VISITA_AGENDADA");
        verify(notificacionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("obtenerPorId - retorna notificación cuando existe")
    void obtenerPorId_RetornarNotificacionExiste() {
        // Given
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacionModel));

        // When
        NotificacionResponse resultado = notificacionService.obtenerPorId(1L);

        // Then
        assertThat(resultado.getIdNotificacion()).isEqualTo(1L);
        assertThat(resultado.getMensaje()).isEqualTo("Tu visita fue agendada para el 25 de junio");
    }

    @Test
    @DisplayName("obtenerPorId - lanza NotFoundException cuando no existe")
    void obtenerPorId_LanzarExcepcionCuandoNoExiste() {
        // Given
        when(notificacionRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> notificacionService.obtenerPorId(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("obtenerPorCliente - retorna notificaciones de un cliente específico")
    void obtenerPorCliente_RetornarNotificacionesDelCliente() {
        // Given
        when(notificacionRepository.findByIdCliente(50L)).thenReturn(List.of(notificacionModel));

        // When
        List<NotificacionResponse> resultado = notificacionService.obtenerPorCliente(50L);

        // Then
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getIdCliente()).isEqualTo(50L);
        verify(notificacionRepository, times(1)).findByIdCliente(50L);
    }

    @Test
    @DisplayName("obtenerNoLeidasPorCliente - retorna solo notificaciones no leídas")
    void obtenerNoLeidasPorCliente_RetornarSoloNoLeidas() {
        // Given
        when(notificacionRepository.findNoLeidasPorCliente(50L)).thenReturn(List.of(notificacionModel));

        // When
        List<NotificacionResponse> resultado = notificacionService.obtenerNoLeidasPorCliente(50L);

        // Then
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getLeida()).isFalse();
        verify(notificacionRepository, times(1)).findNoLeidasPorCliente(50L);
    }

    @Test
    @DisplayName("contarNoLeidas - retorna el total correcto de no leídas")
    void contarNoLeidas_RetornarTotalCorrecto() {
        // Given
        when(notificacionRepository.contarNoLeidasPorCliente(50L)).thenReturn(3L);

        // When
        Long resultado = notificacionService.contarNoLeidas(50L);

        // Then
        assertThat(resultado).isEqualTo(3L);
        verify(notificacionRepository, times(1)).contarNoLeidasPorCliente(50L);
    }

    @Test
    @DisplayName("guardar - persiste notificación y retorna response")
    void guardar_PersistirNotificacionYRetornarResponse() {
        // Given
        when(notificacionRepository.save(any(NotificacionModel.class))).thenReturn(notificacionModel);

        // When
        NotificacionResponse resultado = notificacionService.guardar(notificacionRequest);

        // Then
        assertThat(resultado.getTipo()).isEqualTo("VISITA_AGENDADA");
        assertThat(resultado.getIdCliente()).isEqualTo(50L);
        verify(notificacionRepository, times(1)).save(any(NotificacionModel.class));
    }

    @Test
    @DisplayName("marcarComoLeida - marca notificación existente como leída")
    void marcarComoLeida_MarcarNotificacionComoLeida() {
        // Given
        NotificacionModel notificacionLeida = new NotificacionModel();
        notificacionLeida.setIdNotificacion(1L);
        notificacionLeida.setIdCliente(50L);
        notificacionLeida.setTipo("VISITA_AGENDADA");
        notificacionLeida.setMensaje("Tu visita fue agendada para el 25 de junio");
        notificacionLeida.setFechaEnvio(notificacionModel.getFechaEnvio());
        notificacionLeida.setLeida(true);
        notificacionLeida.setReferencia("VISITA-100");

        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacionModel));
        when(notificacionRepository.save(any(NotificacionModel.class))).thenReturn(notificacionLeida);

        // When
        NotificacionResponse resultado = notificacionService.marcarComoLeida(1L);

        // Then
        assertThat(resultado.getLeida()).isTrue();
        verify(notificacionRepository, times(1)).findById(1L);
        verify(notificacionRepository, times(1)).save(any(NotificacionModel.class));
    }

    @Test
    @DisplayName("marcarComoLeida - lanza NotFoundException cuando notificación no existe")
    void marcarComoLeida_LanzarExcepcionNotificacionNoExiste() {
        // Given
        when(notificacionRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> notificacionService.marcarComoLeida(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");

        verify(notificacionRepository, never()).save(any(NotificacionModel.class));
    }

    @Test
    @DisplayName("eliminar - elimina notificación existente sin errores")
    void eliminar_EliminarNotificacionExistente() {
        // Given
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacionModel));
        doNothing().when(notificacionRepository).deleteById(1L);

        // When
        notificacionService.eliminar(1L);

        // Then
        verify(notificacionRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("eliminar - lanza NotFoundException cuando notificación no existe")
    void eliminar_LanzarExcepcionNotificacionNoExiste() {
        // Given
        when(notificacionRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> notificacionService.eliminar(99L))
                .isInstanceOf(NotFoundException.class);

        verify(notificacionRepository, never()).deleteById(anyLong());
    }
}