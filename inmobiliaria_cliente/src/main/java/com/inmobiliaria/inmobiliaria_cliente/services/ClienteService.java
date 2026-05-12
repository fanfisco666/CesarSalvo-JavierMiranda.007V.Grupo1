package com.inmobiliaria.inmobiliaria_cliente.services;


import com.inmobiliaria.inmobiliaria_cliente.dtos.request.ClienteRequest;
import com.inmobiliaria.inmobiliaria_cliente.dtos.response.ClienteResponse;
import com.inmobiliaria.inmobiliaria_cliente.exceptions.NotFoundException;
import com.inmobiliaria.inmobiliaria_cliente.models.ClienteModel;
import com.inmobiliaria.inmobiliaria_cliente.repositories.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClienteService {

private final ClienteRepository clienteRepository;

public ClienteService(ClienteRepository clienteRepository){
    this.clienteRepository = clienteRepository;}

    public List<ClienteResponse> obtenerTodos() {
        List<ClienteModel> clientes = clienteRepository.findAll();
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
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente con ID " + id + " no encontrado"));

        return ClienteResponse.builder()
                .idCliente(cliente.getIdCliente())
                .rutCliente(cliente.getRutCliente())
                .nombreCliente(cliente.getNombreCliente())
                .apellidosCliente(cliente.getApellidosCliente())
                .correoCliente(cliente.getCorreoCliente())
                .build();
    }

    public ClienteResponse obtenerPorRut(String rut) {
        ClienteModel cliente = clienteRepository.findByRutCliente(rut)
                .orElseThrow(() -> new NotFoundException("Cliente con RUT " + rut + " no encontrado"));
        return ClienteResponse.builder()
                .idCliente(cliente.getIdCliente())
                .rutCliente(cliente.getRutCliente())
                .nombreCliente(cliente.getNombreCliente())
                .apellidosCliente(cliente.getApellidosCliente())
                .correoCliente(cliente.getCorreoCliente())
                .build();
    }

    public ClienteResponse guardar(ClienteRequest dto) {
        ClienteModel modelo = new ClienteModel();
        modelo.setRutCliente(dto.getRutCliente());
        modelo.setNombreCliente(dto.getNombreCliente());
        modelo.setApellidosCliente(dto.getApellidosCliente());
        modelo.setCorreoCliente(dto.getCorreoCliente());
        // guarda en DB
        ClienteModel clienteGuardado = clienteRepository.save(modelo);
        return ClienteResponse.builder()
                .idCliente(clienteGuardado.getIdCliente())
                .rutCliente(clienteGuardado.getRutCliente())
                .nombreCliente(clienteGuardado.getNombreCliente())
                .apellidosCliente(clienteGuardado.getApellidosCliente())
                .correoCliente(clienteGuardado.getCorreoCliente())
                .build();
    }

    public ClienteResponse actualizar(Long id, ClienteRequest dto) {
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente con ID " + id + " no encontrado"));
        cliente.setRutCliente(dto.getRutCliente());
        cliente.setNombreCliente(dto.getNombreCliente());
        cliente.setApellidosCliente(dto.getApellidosCliente());
        cliente.setCorreoCliente(dto.getCorreoCliente());

        ClienteModel clienteActualizado = clienteRepository.save(cliente);
        return ClienteResponse.builder()
                .idCliente(clienteActualizado.getIdCliente())
                .rutCliente(clienteActualizado.getRutCliente())
                .nombreCliente(clienteActualizado.getNombreCliente())
                .apellidosCliente(clienteActualizado.getApellidosCliente())
                .correoCliente(clienteActualizado.getCorreoCliente())
                .build();
    }

    public void eliminar(Long id) {
    clienteRepository.deleteById(id);
    }

}
