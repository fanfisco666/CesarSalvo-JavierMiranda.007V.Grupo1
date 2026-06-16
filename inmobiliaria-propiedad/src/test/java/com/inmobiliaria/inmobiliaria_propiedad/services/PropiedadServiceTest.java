package com.inmobiliaria.inmobiliaria_propiedad.services;


import com.inmobiliaria.inmobiliaria_propiedad.dtos.request.PropiedadRequest;
import com.inmobiliaria.inmobiliaria_propiedad.dtos.response.PropiedadResponse;
import com.inmobiliaria.inmobiliaria_propiedad.exceptions.NotFoundExceptions;
import com.inmobiliaria.inmobiliaria_propiedad.models.PropiedadModel;
import com.inmobiliaria.inmobiliaria_propiedad.repositories.PropiedadRepository;
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
@DisplayName("Test unitarios - PropiedadService")
public class PropiedadServiceTest {

    @Mock
    private PropiedadRepository propiedadRepository;

    @InjectMocks
    private PropiedadService propiedadService;
    private PropiedadModel propiedadModel;
    private PropiedadRequest propiedadRequest;

    @BeforeEach
    void setUp(){
        propiedadModel = new PropiedadModel();
        propiedadModel.setIdPropiedad(1L);
        propiedadModel.setTitulo("CasaTest1");
        propiedadModel.setDireccion("DireccionTest1");
        propiedadModel.setTipo("Test1");
        propiedadModel.setPrecio(1000000.0);
        propiedadModel.setSuperficie(1000.0);
        propiedadModel.setHabitacion(3);
        propiedadModel.setBanno(2);
        propiedadModel.setDescripcion("Descripcion Test 1 Pruebas");

        propiedadRequest = new PropiedadRequest();
        propiedadRequest.setTitulo("CasaTest1");
        propiedadRequest.setDireccion("DireccionTest1");
        propiedadRequest.setTipo("Test1");
        propiedadRequest.setPrecio(1000000.0);
        propiedadRequest.setSuperficie(1000.0);
        propiedadRequest.setHabitacion(3);
        propiedadRequest.setBanno(2);
        propiedadRequest.setDescripcion("Descripcion Test 1 Pruebas");
    }

    @Test
    @DisplayName("obtenerTodos - retorna lista con todos las propiedades")
    void obtenerTodos_RetornarListaPropiedades() {
        //Given
        when(propiedadRepository.findAll()).thenReturn(List.of(propiedadModel));

        //When
        List<PropiedadResponse> resultado = propiedadService.obtenerTodos();

        //Then
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getTitulo()).isEqualTo("CasaTest1");
        verify(propiedadRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("obtenerPorId - retorna propiedad cuando existe")
    void obtenerPorId_RetornarPropiedadExiste() {
        //Given
        when(propiedadRepository.findById(1L)).thenReturn(Optional.of(propiedadModel));

        //When
        PropiedadResponse resultado = propiedadService.obtenerPorId(1L);

        //Then
        assertThat(resultado.getIdPropiedad()).isEqualTo(1L);
        assertThat(resultado.getTipo()).isEqualTo("Test1");
    }

    @Test
    @DisplayName("obtenerPorId - lanza NotFoundExceptions cuando no existe")
    void obtenerPorId_LanzarExcepcionCuandoNoExiste() {
        //Given
        when(propiedadRepository.findById(99L)).thenReturn(Optional.empty());

        //When / Then
        assertThatThrownBy(() -> propiedadService.obtenerPorId(99L))
                .isInstanceOf(NotFoundExceptions.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("guardar - pesiste propiedad y retorna response")
    void guardad_PersistirPropiedadYRetornarResponse() {
        //Given
        when(propiedadRepository.save(any(PropiedadModel.class))).thenReturn(propiedadModel);

        //When
        PropiedadResponse resultado = propiedadService.guardar(propiedadRequest);

        //Then
        assertThat(resultado.getTipo()).isEqualTo("Test1");
        assertThat(resultado.getDireccion()).isEqualTo("DireccionTest1");
        verify(propiedadRepository, times(1)).save(any(PropiedadModel.class));
    }

    @Test
    @DisplayName("eliminar - elimina propiedad existente sin errores")
    void eliminar_EliminarAgenteExistente() {
        //Given
        when(propiedadRepository.findById(1L)).thenReturn(Optional.of(propiedadModel));
        doNothing().when(propiedadRepository).delete(propiedadModel);

        //When
        propiedadService.eliminar(1L);

        //Then
        verify(propiedadRepository, times(1)).delete(propiedadModel);
    }

    @Test
    @DisplayName("eliminar - lanza NotFoundExceptions cuando propiedad no existe")
    void eliminar_LanzarExcepcionPropiedadNoExiste() {
        //Given
        when(propiedadRepository.findById(99L)).thenReturn(Optional.empty());

        //When / Then
        assertThatThrownBy(() -> propiedadService.eliminar(99L))
                .isInstanceOf(NotFoundExceptions.class);
    }

    @Test
    @DisplayName("actualizar - acutaliza agente existente y retorna response actualizado")
    void actualizar_ActualizarAgenteExistente() {
        //Given
        PropiedadRequest requestActualizado = new PropiedadRequest();
        requestActualizado.setTitulo("CasaTest2");
        requestActualizado.setDireccion("DireccionTest2");
        requestActualizado.setTipo("Test2");
        requestActualizado.setPrecio(2000000.0);
        requestActualizado.setSuperficie(2000.0);
        requestActualizado.setHabitacion(4);
        requestActualizado.setBanno(3);
        requestActualizado.setDescripcion("Descripcion Test 2 Pruebas");

        PropiedadModel propiedadActualizado = new PropiedadModel();
        propiedadActualizado.setIdPropiedad(1L);
        propiedadActualizado.setTitulo("CasaTest2");
        propiedadActualizado.setDireccion("DireccionTest2");
        propiedadActualizado.setTipo("Test2");
        propiedadActualizado.setPrecio(2000000.0);
        propiedadActualizado.setSuperficie(2000.0);
        propiedadActualizado.setHabitacion(4);
        propiedadActualizado.setBanno(3);
        propiedadActualizado.setDescripcion("Descripcion Test 2 Pruebas");

        when(propiedadRepository.findById(1L)).thenReturn(Optional.of(propiedadModel));
        when(propiedadRepository.save(any(PropiedadModel.class))).thenReturn(propiedadActualizado);

        //When
        PropiedadResponse resultado = propiedadService.actualizar(1L, requestActualizado);

        //Then
        assertThat(resultado.getTipo()).isEqualTo("Test2");
        assertThat(resultado.getDireccion()).isEqualTo("DireccionTest2");
        assertThat(resultado.getTitulo()).isEqualTo("CasaTest2");
        verify(propiedadRepository, times(1)).findById(1L);
        verify(propiedadRepository, times(1)).save(any(PropiedadModel.class));
    }

    @Test
    @DisplayName("actualizar - lanzaNotFoundExceptions cuando propiedad no existe")
    void actualizar_LanzarExcepcionPropiedadNoExiste() {
        //Given
        when(propiedadRepository.findById(99L)).thenReturn(Optional.empty());

        //When / Then
        assertThatThrownBy(() -> propiedadService.actualizar(99L, propiedadRequest))
                .isInstanceOf(NotFoundExceptions.class)
                .hasMessageContaining("99");
        verify(propiedadRepository, never()).save(any(PropiedadModel.class));
    }
}
