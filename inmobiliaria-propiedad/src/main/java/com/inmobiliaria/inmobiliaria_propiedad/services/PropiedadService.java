package com.inmobiliaria.inmobiliaria_propiedad.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inmobiliaria.inmobiliaria_propiedad.dtos.request.PropiedadRequest;
import com.inmobiliaria.inmobiliaria_propiedad.dtos.response.PropiedadResponse;
import com.inmobiliaria.inmobiliaria_propiedad.exceptions.NotFoundExceptions;
import com.inmobiliaria.inmobiliaria_propiedad.models.PropiedadModel;
import com.inmobiliaria.inmobiliaria_propiedad.repositories.PropiedadRepository;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class PropiedadService {

    private final PropiedadRepository propiedadRepository;

    public PropiedadService(PropiedadRepository propiedadRepository) {
        this.propiedadRepository = propiedadRepository;
    }

    // para listar todas las propiedades
    public List<PropiedadResponse> obtenerTodos() {
        log.info("Obteniendo lista de todas las propiedades");
        log.warn("Este es un mensaje de advertencia");
        List<PropiedadModel> propiedades = propiedadRepository.findAll();
        return propiedades.stream().map(propiedad -> PropiedadResponse.builder()
                .idPropiedad(propiedad.getIdPropiedad())
                .titulo(propiedad.getTitulo())
                .direccion(propiedad.getDireccion())
                .tipo(propiedad.getTipo())
                .precio(propiedad.getPrecio())
                .estado(propiedad.isEstado())
                .superficie(propiedad.getSuperficie())
                .habitacion(propiedad.getHabitacion())
                .banno(propiedad.getBanno())
                .descripcion(propiedad.getDescripcion())
                .build()).toList();
    }

    // para guardar una propiedad.
    public PropiedadResponse guardar(PropiedadRequest dto) {
        log.info("Guardando nueva propiedad");
        log.debug("Datos Recibidos. Título={}", dto.getTitulo());
        PropiedadModel modelo = new PropiedadModel();
        modelo.setTitulo(dto.getTitulo());
        modelo.setDireccion(dto.getDireccion());
        modelo.setTipo(dto.getTipo());
        modelo.setPrecio(dto.getPrecio());
        modelo.setEstado(dto.isEstado());
        modelo.setSuperficie(dto.getSuperficie());
        modelo.setHabitacion(dto.getHabitacion());
        modelo.setBanno(dto.getBanno());
        modelo.setDescripcion(dto.getDescripcion());

        PropiedadModel propiedadGuardada = propiedadRepository.save(modelo);
        log.info("Propiedad guardada con ID: {}", propiedadGuardada.getIdPropiedad());
        return PropiedadResponse.builder()
                .idPropiedad(propiedadGuardada.getIdPropiedad())
                .titulo(propiedadGuardada.getTitulo())
                .direccion(propiedadGuardada.getDireccion())
                .tipo(propiedadGuardada.getTipo())
                .precio(propiedadGuardada.getPrecio())
                .estado(propiedadGuardada.isEstado())
                .superficie(propiedadGuardada.getSuperficie())
                .habitacion(propiedadGuardada.getHabitacion())
                .banno(propiedadGuardada.getBanno())
                .descripcion(propiedadGuardada.getDescripcion())
                .build();

    }

    // para actualizar una propiedad
    public PropiedadResponse actualizar(Long id, PropiedadRequest dto) {
        log.info("Actualizando propiedad con ID: {}", id);
        log.debug("Datos Recibidos para actualización. ID={}, Título={}", id, dto.getTitulo());
        PropiedadModel propiedad = propiedadRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Propiedad no encontrada. ID={}", id);
                    return new NotFoundExceptions("Propiedad con ID " + id + " no encontrada");
                });
        propiedad.setIdPropiedad(id);
        propiedad.setTitulo(dto.getTitulo());
        propiedad.setDireccion(dto.getDireccion());
        propiedad.setTipo(dto.getTipo());
        propiedad.setPrecio(dto.getPrecio());
        propiedad.setEstado(dto.isEstado());
        propiedad.setSuperficie(dto.getSuperficie());
        propiedad.setHabitacion(dto.getHabitacion());
        propiedad.setBanno(dto.getBanno());
        propiedad.setDescripcion(dto.getDescripcion());

        PropiedadModel propiedadActualizada = propiedadRepository.save(propiedad);
        log.info("Propiedad con ID {} actualizada", id);
        return PropiedadResponse.builder()
                .idPropiedad(propiedadActualizada.getIdPropiedad())
                .titulo(propiedadActualizada.getTitulo())
                .direccion(propiedadActualizada.getDireccion())
                .tipo(propiedadActualizada.getTipo())
                .precio(propiedadActualizada.getPrecio())
                .estado(propiedadActualizada.isEstado())
                .superficie(propiedadActualizada.getSuperficie())
                .habitacion(propiedadActualizada.getHabitacion())
                .banno(propiedadActualizada.getBanno())
                .descripcion(propiedadActualizada.getDescripcion())
                .build();

    }

    public PropiedadResponse obtenerPorId(Long id) {
        log.info("Buscando propiedad con ID: {}", id);

        PropiedadModel propiedad = propiedadRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Propiedad no encontrada. ID={}", id);
                    return new NotFoundExceptions(
                            "Propiedad con ID " + id + " no encontrada");
                });

        return PropiedadResponse.builder()
                .idPropiedad(propiedad.getIdPropiedad())
                .titulo(propiedad.getTitulo())
                .direccion(propiedad.getDireccion())
                .tipo(propiedad.getTipo())
                .precio(propiedad.getPrecio())
                .estado(propiedad.isEstado())
                .superficie(propiedad.getSuperficie())
                .habitacion(propiedad.getHabitacion())
                .banno(propiedad.getBanno())
                .descripcion(propiedad.getDescripcion())
                .build();
    }

    // para eliminar una propiedad.
    public void eliminar(@NonNull Long id) {
        log.info("Eliminando propiedad con ID: {}", id);
        PropiedadModel propiedad = propiedadRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Propiedad no encontrada. ID={}", id);
                    return new NotFoundExceptions("Propiedad con ID " + id + " no encontrada");
                });
        propiedadRepository.delete(propiedad);
        log.info("Propiedad con ID {} eliminada", id);
    }

}
