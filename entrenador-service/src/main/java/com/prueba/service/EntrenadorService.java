package com.prueba.service;

import com.prueba.dto.EntrenadorRequest;
import com.prueba.dto.EntrenadorResponse;
import com.prueba.exception.NotFoundException;
import com.prueba.mapper.EntrenadorMapper;
import com.prueba.model.Entrenador;
import com.prueba.repository.EntrenadorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EntrenadorService {

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    @Autowired
    private EntrenadorMapper entrenadorMapper;

    public EntrenadorResponse crear(EntrenadorRequest request) {
        log.info("Creando entrenador con nombre: {}", request.getNombre());
        Entrenador entrenador = entrenadorMapper.dtoToObject(request);
        Entrenador guardado = entrenadorRepository.save(entrenador);
        log.info("Entrenador creado correctamente con id: {}", guardado.getId());
        return entrenadorMapper.toResponse(guardado);
    }

    public List<EntrenadorResponse> obtenerEntrenadores() {
        log.info("Obteniendo listado de entrenadores");
        return entrenadorRepository.findAll()
                .stream()
                .map(entrenadorMapper::toResponse)
                .toList();
    }

    public EntrenadorResponse obtenerEntrenador(Long id) {
        log.info("Buscando entrenador con id: {}", id);
        Entrenador entrenador = entrenadorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontró el entrenador con id: {}", id);
                    return new NotFoundException("No se encontró al entrenador con id " + id);
                });
        return entrenadorMapper.toResponse(entrenador);
    }

    public EntrenadorResponse modificar(Long id, EntrenadorRequest request) {
        log.info("Modificando entrenador con id: {}", id);
        Entrenador entrenador = entrenadorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró al entrenador con id " + id));

        entrenador.setNombre(request.getNombre());
        entrenador.setEspecialidad(request.getEspecialidad());
        entrenador.setTelefono(request.getTelefono());
        entrenador.setActivo(request.getActivo());

        Entrenador actualizado = entrenadorRepository.save(entrenador);
        log.info("Entrenador actualizado correctamente con id: {}", actualizado.getId());
        return entrenadorMapper.toResponse(actualizado);
    }

    public void eliminar(Long id) {
        log.info("Eliminando entrenador con id: {}", id);
        if (!entrenadorRepository.existsById(id)) {
            throw new NotFoundException("No se encontró al entrenador con id " + id);
        }
        entrenadorRepository.deleteById(id);
        log.info("Entrenador eliminado correctamente con id: {}", id);
    }
}
