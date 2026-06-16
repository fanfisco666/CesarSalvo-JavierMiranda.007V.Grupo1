package com.inmobiliaria.inmobiliaria_cliente.services;

import com.inmobiliaria.inmobiliaria_cliente.dtos.request.ClienteRequest;
import com.inmobiliaria.inmobiliaria_cliente.dtos.response.ClienteResponse;
import com.inmobiliaria.inmobiliaria_cliente.exceptions.NotFoundException;
import com.inmobiliaria.inmobiliaria_cliente.models.ClienteModel;
import com.inmobiliaria.inmobiliaria_cliente.repositories.ClienteRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteResponse> obtenerTodos() {
        log.info("Obteniendo lista de todos los clientes");
        List<ClienteModel> clientes = clienteRepository.findAll();
        log.info("Se encontraron {} clientes", clientes.size());
        return clientes.stream().map(cliente -> ClienteResponse.builder()
                .idCliente(cliente.getIdCliente())
                .rutCliente(cliente.getRutCliente())
                .nombreCliente(cliente.getNombreCliente())
                .apellidosCliente(cliente.getApellidosCliente())
                .correoCliente(cliente.getCorreoCliente())
                .build()
        ).toList();
    }

    public ClienteResponse obtenerPorId(Long id) {
        log.info("Buscando cliente con ID: {}", id);
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cliente con ID {} no encontrado", id);
                    return new NotFoundException("Cliente con ID " + id + " no encontrado");
                });
        log.info("Cliente con ID {} encontrado: {}", id, cliente.getNombreCliente());
        return ClienteResponse.builder()
                .idCliente(cliente.getIdCliente())
                .rutCliente(cliente.getRutCliente())
                .nombreCliente(cliente.getNombreCliente())
                .apellidosCliente(cliente.getApellidosCliente())
                .correoCliente(cliente.getCorreoCliente())
                .build();
    }

    public ClienteResponse obtenerPorRut(String rut) {
        log.info("Buscando cliente con RUT: {}", rut);
        ClienteModel cliente = clienteRepository.findByRutCliente(rut)
                .orElseThrow(() -> {
                    log.warn("Cliente con RUT {} no encontrado", rut);
                    return new NotFoundException("Cliente con RUT " + rut + " no encontrado");
                });
        log.info("Cliente con RUT {} encontrado: {}", rut, cliente.getNombreCliente());
        return ClienteResponse.builder()
                .idCliente(cliente.getIdCliente())
                .rutCliente(cliente.getRutCliente())
                .nombreCliente(cliente.getNombreCliente())
                .apellidosCliente(cliente.getApellidosCliente())
                .correoCliente(cliente.getCorreoCliente())
                .build();
    }

    public ClienteResponse guardar(ClienteRequest dto) {
        log.info("Guardando nuevo cliente con RUT: {}", dto.getRutCliente());
        ClienteModel modelo = new ClienteModel();
        modelo.setRutCliente(dto.getRutCliente());
        modelo.setNombreCliente(dto.getNombreCliente());
        modelo.setApellidosCliente(dto.getApellidosCliente());
        modelo.setCorreoCliente(dto.getCorreoCliente());
        ClienteModel clienteGuardado = clienteRepository.save(modelo);
        log.info("Cliente guardado exitosamente con ID: {}", clienteGuardado.getIdCliente());
        return ClienteResponse.builder()
                .idCliente(clienteGuardado.getIdCliente())
                .rutCliente(clienteGuardado.getRutCliente())
                .nombreCliente(clienteGuardado.getNombreCliente())
                .apellidosCliente(clienteGuardado.getApellidosCliente())
                .correoCliente(clienteGuardado.getCorreoCliente())
                .build();
    }

    public ClienteResponse actualizar(Long id, ClienteRequest dto) {
        log.info("Actualizando cliente con ID: {}", id);
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cliente con ID {} no encontrado para actualizar", id);
                    return new NotFoundException("Cliente con ID " + id + " no encontrado");
                });
        cliente.setRutCliente(dto.getRutCliente());
        cliente.setNombreCliente(dto.getNombreCliente());
        cliente.setApellidosCliente(dto.getApellidosCliente());
        cliente.setCorreoCliente(dto.getCorreoCliente());
        ClienteModel clienteActualizado = clienteRepository.save(cliente);
        log.info("Cliente con ID {} actualizado exitosamente", clienteActualizado.getIdCliente());
        return ClienteResponse.builder()
                .idCliente(clienteActualizado.getIdCliente())
                .rutCliente(clienteActualizado.getRutCliente())
                .nombreCliente(clienteActualizado.getNombreCliente())
                .apellidosCliente(clienteActualizado.getApellidosCliente())
                .correoCliente(clienteActualizado.getCorreoCliente())
                .build();
    }

    public void eliminar(Long id) {
        log.warn("Eliminando cliente con ID: {}", id);
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cliente con ID {} no encontrado", id);
                    return new NotFoundException("Cliente con ID " + id + " no encontrado");
                });
        clienteRepository.delete(cliente);
        log.info("Cliente con ID {} eliminado exitosamente", id);
    }
}
