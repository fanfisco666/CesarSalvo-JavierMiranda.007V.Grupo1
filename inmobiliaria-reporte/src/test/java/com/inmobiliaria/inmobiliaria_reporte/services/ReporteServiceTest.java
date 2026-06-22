package com.inmobiliaria.inmobiliaria_reporte.services;

import com.inmobiliaria.inmobiliaria_reporte.client.AgenteClient;
import com.inmobiliaria.inmobiliaria_reporte.client.ClienteClient;
import com.inmobiliaria.inmobiliaria_reporte.client.PropiedadClient;
import com.inmobiliaria.inmobiliaria_reporte.dtos.request.ReporteRequest;
import com.inmobiliaria.inmobiliaria_reporte.dtos.response.AgenteResponse;
import com.inmobiliaria.inmobiliaria_reporte.dtos.response.ClienteResponse;
import com.inmobiliaria.inmobiliaria_reporte.dtos.response.PropiedadResponse;
import com.inmobiliaria.inmobiliaria_reporte.dtos.response.ReporteResponse;
import com.inmobiliaria.inmobiliaria_reporte.exceptions.NotFoundExceptions;
import com.inmobiliaria.inmobiliaria_reporte.exceptions.RemoteServiceExceptions;
import com.inmobiliaria.inmobiliaria_reporte.models.ReporteModel;
import com.inmobiliaria.inmobiliaria_reporte.repositories.ReporteRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - ReporteService")
class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @Mock
    private PropiedadClient propiedadClient;

    @Mock
    private AgenteClient agenteClient;

    @Mock
    private ClienteClient clienteClient;

    @InjectMocks
    private ReporteService reporteService;

    private ReporteModel reporteModel;
    private ReporteRequest reporteRequest;

    @BeforeEach
    void setUp() {
        reporteModel = new ReporteModel();
        reporteModel.setIdReporte(1L);
        reporteModel.setTitulo("Filtración en el techo");
        reporteModel.setDescripcion("Se observa filtración de agua en el dormitorio principal");
        reporteModel.setTipoReporte("mantenimiento");
        reporteModel.setIdPropiedad(20L);
        reporteModel.setIdUsuario(30L);
        reporteModel.setIdAgente(10L);

        reporteRequest = new ReporteRequest();
        reporteRequest.setTitulo("Filtración en el techo");
        reporteRequest.setDescripcion("Se observa filtración de agua en el dormitorio principal");
        reporteRequest.setTipoReporte("mantenimiento");
        reporteRequest.setIdPropiedad(20L);
        reporteRequest.setIdUsuario(30L);
        reporteRequest.setIdAgente(10L);

    }

    @Test
    @DisplayName("obtenerReportes - retorna lista con todos los reportes")
    void obtenerReportes_RetornarListaReportes() {
        // Given
        when(reporteRepository.findAll()).thenReturn(List.of(reporteModel));

        // When
        List<ReporteResponse> resultado = reporteService.obtenerReportes();

        // Then
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getTitulo()).isEqualTo("Filtración en el techo");
        verify(reporteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("obtenerPorId - retorna reporte cuando existe")
    void obtenerPorId_RetornarReporteExiste() {
        // Given
        when(reporteRepository.findById(1L)).thenReturn(Optional.of(reporteModel));

        // When
        ReporteResponse resultado = reporteService.obtenerPorId(1L);

        // Then
        assertThat(resultado.getIdReporte()).isEqualTo(1L);
        assertThat(resultado.getTipoReporte()).isEqualTo("mantenimiento");
    }

    @Test
    @DisplayName("obtenerPorId - lanza NotFoundExceptions cuando no existe")
    void obtenerPorId_LanzarExcepcionCuandoNoExiste() {
        // Given
        when(reporteRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> reporteService.obtenerPorId(99L))
                .isInstanceOf(NotFoundExceptions.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("obtenerPorId - arma response con datos parciales si un Feign Client falla")
    void obtenerPorId_ArmarResponseParcialSiFeignFalla() {

        PropiedadResponse propiedad = new PropiedadResponse();
        propiedad.setIdPropiedad(20L);

        when(reporteRepository.findById(1L)).thenReturn(Optional.of(reporteModel));
        when(propiedadClient.obtenerPropiedadPorId(20L)).thenReturn(propiedad);
        when(agenteClient.obtenerAgentePorId(10L)).thenThrow(new RuntimeException("Servicio caído"));
        when(clienteClient.obtenerClientePorId(30L)).thenReturn(null);

        // When
        ReporteResponse resultado = reporteService.obtenerPorId(1L);

        assertThat(resultado.getPropiedad()).isNotNull();
        assertThat(resultado.getAgente()).isNull();
    }

    @Test
    @DisplayName("guardar - persiste reporte cuando propiedad, agente y cliente existen")
    void guardar_PersistirReporteCuandoTodoValido() {
        // Given: las 3 validaciones pasan sin lanzar excepción
        when(propiedadClient.obtenerPropiedadPorId(20L)).thenReturn(new PropiedadResponse());
        when(agenteClient.obtenerAgentePorId(10L)).thenReturn(new AgenteResponse());
        when(clienteClient.obtenerClientePorId(30L)).thenReturn(new ClienteResponse());
        when(reporteRepository.save(any(ReporteModel.class))).thenReturn(reporteModel);

        // When
        ReporteResponse resultado = reporteService.guardar(reporteRequest);

        // Then
        assertThat(resultado.getTitulo()).isEqualTo("Filtración en el techo");
        verify(reporteRepository, times(1)).save(any(ReporteModel.class));
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceExceptions cuando la propiedad no existe")
    void guardar_LanzarExcepcionCuandoPropiedadNoExiste() {

        when(propiedadClient.obtenerPropiedadPorId(20L)).thenThrow(new RuntimeException("404"));

        // When / Then
        assertThatThrownBy(() -> reporteService.guardar(reporteRequest))
                .isInstanceOf(RemoteServiceExceptions.class)
                .hasMessageContaining("Propiedad");

        verify(reporteRepository, never()).save(any(ReporteModel.class));
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceExceptions cuando el agente no existe")
    void guardar_LanzarExcepcionCuandoAgenteNoExiste() {

        when(propiedadClient.obtenerPropiedadPorId(20L)).thenReturn(new PropiedadResponse());
        when(agenteClient.obtenerAgentePorId(10L)).thenThrow(new RuntimeException("404"));

        // When / Then
        assertThatThrownBy(() -> reporteService.guardar(reporteRequest))
                .isInstanceOf(RemoteServiceExceptions.class)
                .hasMessageContaining("Agente");

        verify(reporteRepository, never()).save(any(ReporteModel.class));
    }

    @Test
    @DisplayName("guardar - lanza RemoteServiceExceptions cuando el cliente no existe")
    void guardar_LanzarExcepcionCuandoClienteNoExiste() {
        // Given: propiedad y agente existen, pero el cliente falla
        when(propiedadClient.obtenerPropiedadPorId(20L)).thenReturn(new PropiedadResponse());
        when(agenteClient.obtenerAgentePorId(10L)).thenReturn(new AgenteResponse());
        when(clienteClient.obtenerClientePorId(30L)).thenThrow(new RuntimeException("404"));

        // When / Then
        assertThatThrownBy(() -> reporteService.guardar(reporteRequest))
                .isInstanceOf(RemoteServiceExceptions.class)
                .hasMessageContaining("Cliente");

        verify(reporteRepository, never()).save(any(ReporteModel.class));
    }

    @Test
    @DisplayName("actualizar - actualiza reporte existente cuando los 3 recursos son válidos")
    void actualizar_ActualizarReporteExistente() {
        // Given
        ReporteRequest requestActualizado = new ReporteRequest();
        requestActualizado.setTitulo("Filtración resuelta");
        requestActualizado.setDescripcion("Se reparó la filtración del dormitorio principal");
        requestActualizado.setTipoReporte("mantenimiento");
        requestActualizado.setIdPropiedad(20L);
        requestActualizado.setIdUsuario(30L);
        requestActualizado.setIdAgente(10L);

        ReporteModel reporteActualizado = new ReporteModel();
        reporteActualizado.setIdReporte(1L);
        reporteActualizado.setTitulo("Filtración resuelta");
        reporteActualizado.setDescripcion("Se reparó la filtración del dormitorio principal");
        reporteActualizado.setTipoReporte("mantenimiento");
        reporteActualizado.setIdPropiedad(20L);
        reporteActualizado.setIdUsuario(30L);
        reporteActualizado.setIdAgente(10L);

        when(reporteRepository.findById(1L)).thenReturn(Optional.of(reporteModel));
        when(propiedadClient.obtenerPropiedadPorId(20L)).thenReturn(new PropiedadResponse());
        when(agenteClient.obtenerAgentePorId(10L)).thenReturn(new AgenteResponse());
        when(clienteClient.obtenerClientePorId(30L)).thenReturn(new ClienteResponse());
        when(reporteRepository.save(any(ReporteModel.class))).thenReturn(reporteActualizado);

        // When
        ReporteResponse resultado = reporteService.actualizar(1L, requestActualizado);

        // Then
        assertThat(resultado.getTitulo()).isEqualTo("Filtración resuelta");
        verify(reporteRepository, times(1)).save(any(ReporteModel.class));
    }

    @Test
    @DisplayName("actualizar - lanza NotFoundExceptions cuando el reporte no existe")
    void actualizar_LanzarExcepcionReporteNoExiste() {
        // Given
        when(reporteRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> reporteService.actualizar(99L, reporteRequest))
                .isInstanceOf(NotFoundExceptions.class)
                .hasMessageContaining("99");

        verify(reporteRepository, never()).save(any(ReporteModel.class));
    }

    @Test
    @DisplayName("eliminar - elimina reporte existente sin errores")
    void eliminar_EliminarReporteExistente() {
        // Given
        when(reporteRepository.findById(1L)).thenReturn(Optional.of(reporteModel));
        doNothing().when(reporteRepository).delete(reporteModel);

        // When
        reporteService.eliminar(1L);

        // Then
        verify(reporteRepository, times(1)).delete(reporteModel);
    }

    @Test
    @DisplayName("eliminar - lanza NotFoundExceptions cuando el reporte no existe")
    void eliminar_LanzarExcepcionReporteNoExiste() {
        // Given
        when(reporteRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> reporteService.eliminar(99L))
                .isInstanceOf(NotFoundExceptions.class);

        verify(reporteRepository, never()).delete(any(ReporteModel.class));
    }
}