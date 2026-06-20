package com.prueba.service;

import com.prueba.DTO.AsistenciaRequest;
import com.prueba.DTO.AsistenciaResponse;
import com.prueba.exception.NotFoundException;
import com.prueba.mapper.AsistenciaMapper;
import com.prueba.model.Asistencia;
import com.prueba.repository.AsistenciaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private AsistenciaMapper asistenciaMapper;

    public AsistenciaResponse registrarEntrada(AsistenciaRequest request) {
        log.info("Registrando entrada para {}", request.getNombre());

        Asistencia asistencia = asistenciaMapper.toEntity(request);
        asistencia.setRegistroEntrada(LocalDateTime.now());

        Asistencia saved = asistenciaRepository.save(asistencia);
        return asistenciaMapper.toResponse(saved);
    }

    public AsistenciaResponse registrarSalida(Long id) {
        Asistencia asistencia = asistenciaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registro no encontrado"));

        asistencia.setRegistroSalida(LocalDateTime.now());
        Asistencia updated = asistenciaRepository.save(asistencia);

        log.info("Salida registrada para {}", asistencia.getNombre());
        return asistenciaMapper.toResponse(updated);
    }

    public List<AsistenciaResponse> listarAsistencias() {
        log.info("Listando asistencias");
        return asistenciaRepository.findAll()
                .stream()
                .map(asistenciaMapper::toResponse)
                .toList();
    }

    public AsistenciaResponse obtenerPorId(Long id) {
        log.info("Buscando asistencia con id {}", id);
        Asistencia asistencia = asistenciaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registro no encontrado"));
        return asistenciaMapper.toResponse(asistencia);
    }

    public void eliminar(Long id) {
        Asistencia asistencia = asistenciaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registro no encontrado"));
        asistenciaRepository.delete(asistencia);
        log.info("Asistencia eliminada {}", id);
    }
}
