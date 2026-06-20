package com.prueba.service;

import com.prueba.dto.TipoMembresiaRequest;
import com.prueba.dto.TipoMembresiaResponse;
import com.prueba.exception.NotFoundException;
import com.prueba.mapper.TipoMembresiaMapper;
import com.prueba.model.TipoMembresia;
import com.prueba.repository.TipoMembresiaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TipoMembresiaService {

    @Autowired
    private TipoMembresiaRepository tipoMembresiaRepository;

    @Autowired
    private TipoMembresiaMapper tipoMembresiaMapper;

    public TipoMembresiaResponse crear(TipoMembresiaRequest request) {
        log.info("Creando tipo de membresía con nombre: {}", request.getNombre());
        TipoMembresia tipoMembresia = tipoMembresiaMapper.dtoToObject(request);
        TipoMembresia guardado = tipoMembresiaRepository.save(tipoMembresia);
        log.info("Tipo de membresía creado con id: {}", guardado.getId());
        return tipoMembresiaMapper.toResponse(guardado);
    }

    public List<TipoMembresiaResponse> obtenerTipoMembresias() {
        log.info("Obteniendo listado de tipos de membresía");
        return tipoMembresiaRepository.findAll()
                .stream()
                .map(tipoMembresiaMapper::toResponse)
                .toList();
    }

    public TipoMembresiaResponse obtenerTipoMembresia(Long id) {
        log.info("Buscando tipo de membresía con id: {}", id);
        TipoMembresia tipoMembresia = tipoMembresiaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontró el tipo de membresía con id: {}", id);
                    return new NotFoundException("No se encontró el tipo de membresía con id " + id);
                });
        return tipoMembresiaMapper.toResponse(tipoMembresia);
    }

    public TipoMembresiaResponse modificar(Long id, TipoMembresiaRequest request) {
        log.info("Modificando tipo de membresía con id: {}", id);
        TipoMembresia tipoMembresia = tipoMembresiaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el tipo de membresía con id " + id));

        tipoMembresia.setNombre(request.getNombre());
        tipoMembresia.setDescripcion(request.getDescripcion());
        tipoMembresia.setPrecio(request.getPrecio());
        tipoMembresia.setDuracionDias(request.getDuracionDias());

        TipoMembresia actualizado = tipoMembresiaRepository.save(tipoMembresia);
        log.info("Tipo de membresía actualizado con id: {}", actualizado.getId());
        return tipoMembresiaMapper.toResponse(actualizado);
    }

    public void eliminar(Long id) {
        log.info("Eliminando tipo de membresía con id: {}", id);
        if (!tipoMembresiaRepository.existsById(id)) {
            throw new NotFoundException("No se encontró el tipo de membresía con id " + id);
        }
        tipoMembresiaRepository.deleteById(id);
        log.info("Tipo de membresía eliminado con id: {}", id);
    }
}
