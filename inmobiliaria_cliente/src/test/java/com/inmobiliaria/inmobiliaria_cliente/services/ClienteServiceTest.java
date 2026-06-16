package com.inmobiliaria.inmobiliaria_cliente.services;

import com.inmobiliaria.inmobiliaria_cliente.dtos.request.ClienteRequest;
import com.inmobiliaria.inmobiliaria_cliente.dtos.response.ClienteResponse;
import com.inmobiliaria.inmobiliaria_cliente.exceptions.NotFoundException;
import com.inmobiliaria.inmobiliaria_cliente.models.ClienteModel;
import com.inmobiliaria.inmobiliaria_cliente.repositories.ClienteRepository;
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
@DisplayName("Tests unitarios - ClienteService")
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;
    private ClienteModel clienteModel;
    private ClienteRequest clienteRequest;

    @BeforeEach
    void setUp() {
        clienteModel = new ClienteModel();
        clienteModel.setIdCliente(1L);
        clienteModel.setRutCliente("12345678-9");
        clienteModel.setNombreCliente("Juan");
        clienteModel.setApellidosCliente("Pérez");
        clienteModel.setCorreoCliente("juan@test.com");

        clienteRequest = new ClienteRequest();
        clienteRequest.setRutCliente("12345678-9");
        clienteRequest.setNombreCliente("Juan");
        clienteRequest.setApellidosCliente("Pérez");
        clienteRequest.setCorreoCliente("juan@test.com");
    }

    @Test
    @DisplayName("obtenerTodos - retorna lista de clientes")
    void obtenerTodos_RetornarListaClientes() {
        // Given
        when(clienteRepository.findAll()).thenReturn(List.of(clienteModel));

        // When
        List<ClienteResponse> resultado = clienteService.obtenerTodos();

        // Then
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombreCliente()).isEqualTo("Juan");
        assertThat(resultado.get(0).getRutCliente()).isEqualTo("12345678-9");
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("obtenerTodos - retorna lista vacía cuando no hay clientes")
    void obtenerTodos_RetornarListaVacia() {
        // Given
        when(clienteRepository.findAll()).thenReturn(List.of());

        // When
        List<ClienteResponse> resultado = clienteService.obtenerTodos();

        // Then
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("obtenerPorId - retorna cliente cuando existe")
    void obtenerPorId_RetornarClienteExiste() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteModel));

        // When
        ClienteResponse resultado = clienteService.obtenerPorId(1L);

        // Then
        assertThat(resultado.getIdCliente()).isEqualTo(1L);
        assertThat(resultado.getNombreCliente()).isEqualTo("Juan");
        assertThat(resultado.getCorreoCliente()).isEqualTo("juan@test.com");
    }

    @Test
    @DisplayName("obtenerPorId - lanza NotFoundException cuando no existe")
    void obtenerPorId_LanzarExcepcionCuandoNoExiste() {
        // Given
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> clienteService.obtenerPorId(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("guardar - crea cliente correctamente")
    void guardar_deberiaCrearClienteCorrectamente() {
        // Given
        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(clienteModel);

        // When
        ClienteResponse resultado = clienteService.guardar(clienteRequest);

        // Then
        assertThat(resultado.getRutCliente()).isEqualTo("12345678-9");
        assertThat(resultado.getNombreCliente()).isEqualTo("Juan");
        verify(clienteRepository, times(1)).save(any(ClienteModel.class));
    }

    @Test
    @DisplayName("eliminar - elimina cliente por ID")
    void eliminar_EliminarClientePorId() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteModel));
        doNothing().when(clienteRepository).delete(clienteModel);

        // When
        clienteService.eliminar(1L);

        // Then
        verify(clienteRepository, times(1)).delete(clienteModel);
    }

    @Test
    @DisplayName("eliminar - lanza NotFoundExceptions cuando cliente no existe")
    void eliminar_LanzarExcepcionClienteNoExiste() {
        // Given
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> clienteService.eliminar(99L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("actualizar - actualiza cliente existente")
    void actualizar_deberiaActualizarClienteExistente() {
        // Given
        ClienteRequest requestActualizado = new ClienteRequest();
        requestActualizado.setRutCliente("12345678-9");
        requestActualizado.setNombreCliente("Juan Actualizado");
        requestActualizado.setApellidosCliente("Pérez");
        requestActualizado.setCorreoCliente("juan_nuevo@test.com");

        ClienteModel modeloActualizado = new ClienteModel();
        modeloActualizado.setIdCliente(1L);
        modeloActualizado.setNombreCliente("Juan Actualizado");
        modeloActualizado.setRutCliente("12345678-9");
        modeloActualizado.setApellidosCliente("Pérez");
        modeloActualizado.setCorreoCliente("juan_nuevo@test.com");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteModel));
        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(modeloActualizado);

        // When
        ClienteResponse resultado = clienteService.actualizar(1L, requestActualizado);

        // Then
        assertThat(resultado.getNombreCliente()).isEqualTo("Juan Actualizado");
        assertThat(resultado.getCorreoCliente()).isEqualTo("juan_nuevo@test.com");
    }

    @Test
    @DisplayName("actualizar - lanza NotFoundException cuando cliente no existe")
    void actualizar_deberiaLanzarExcepcionCuandoClienteNoExiste() {
        // Given
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> clienteService.actualizar(99L, clienteRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
        verify(clienteRepository, never()).save(any(ClienteModel.class));
    }


}
