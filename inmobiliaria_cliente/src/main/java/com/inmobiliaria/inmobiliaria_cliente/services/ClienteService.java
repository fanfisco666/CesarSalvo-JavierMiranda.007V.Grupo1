package com.inmobiliaria.inmobiliaria_cliente.services;


import com.inmobiliaria.inmobiliaria_cliente.models.ClienteModel;
import com.inmobiliaria.inmobiliaria_cliente.repositories.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

private final ClienteRepository clienteRepository;

public ClienteService(ClienteRepository clienteRepository){
    this.clienteRepository = clienteRepository;}

    public List<ClienteModel> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Optional<ClienteModel> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<ClienteModel> obtenerPorRut(String rut) {
    return clienteRepository.findByRut(rut);
    }

    public ClienteModel guardar(ClienteModel cliente) {
    return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {
    clienteRepository.deleteById(id);
    }

}
