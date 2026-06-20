package com.prueba.service;

import com.prueba.dto.ClaseRequest;
import com.prueba.dto.ClaseResponse;
import com.prueba.exception.BusinessException;
import com.prueba.exception.NotFoundException;
import com.prueba.mapper.ClaseMapper;
import com.prueba.model.Clase;
import com.prueba.repository.ClaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private ClaseMapper claseMapper;

    public ClaseResponse crearClase(ClaseRequest request) {
        log.info("Intentando crear clase {}", request.getNombre());

        if (request.getCapacidad() <= 0) {
            throw new BusinessException("La capacidad debe ser mayor a 0");
        }

        boolean existe = claseRepository.findAll()
                .stream()
                .anyMatch(c -> c.getNombre().equalsIgnoreCase(request.getNombre()));

        if (existe) {
            throw new BusinessException("Ya existe una clase con ese nombre");
        }

        Clase clase = claseMapper.toEntity(request);
        Clase saved = claseRepository.save(clase);
        log.info("Clase creada con id {}", saved.getId());
        return claseMapper.toResponse(saved);
    }

    public List<ClaseResponse> listarClases() {
        log.info("Listando clases");
        return claseRepository.findAll()
                .stream()
                .map(claseMapper::toResponse)
                .toList();
    }

    public ClaseResponse obtenerPorId(Long id) {
        log.info("Buscando clase {}", id);
        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Clase no encontrada"));
        return claseMapper.toResponse(clase);
    }

    public ClaseResponse modificarClase(Long id, ClaseRequest request) {
        log.info("Modificando clase {}", id);
        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Clase no encontrada"));

        clase.setNombre(request.getNombre());
        clase.setInstructor(request.getInstructor());
        clase.setCapacidad(request.getCapacidad());
        clase.setEntrenadorId(request.getEntrenadorId());

        Clase updated = claseRepository.save(clase);
        log.info("Clase modificada {}", id);
        return claseMapper.toResponse(updated);
    }

    public void eliminarClase(Long id) {
        log.info("Eliminando clase {}", id);
        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Clase no encontrada"));
        claseRepository.delete(clase);
        log.info("Clase eliminada {}", id);
    }
}
