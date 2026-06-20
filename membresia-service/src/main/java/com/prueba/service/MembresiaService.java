package com.prueba.service;

import com.prueba.dto.MembresiaRequest;
import com.prueba.dto.MembresiaResponse;
import com.prueba.exception.BusinessException;
import com.prueba.exception.NotFoundException;
import com.prueba.mapper.MembresiaMapper;
import com.prueba.model.Membresia;
import com.prueba.model.TipoMembresia;
import com.prueba.repository.MembresiaRepository;
import com.prueba.repository.TipoMembresiaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class MembresiaService {

    @Autowired
    private MembresiaRepository membresiaRepository;

    @Autowired
    private TipoMembresiaRepository tipoMembresiaRepository;

    @Autowired
    private MembresiaMapper membresiaMapper;

    public MembresiaResponse crear(MembresiaRequest request) {
        log.info("Creando membresía para clienteId {}", request.getClienteId());

        if (membresiaRepository.existsByClienteId(request.getClienteId())) {
            throw new BusinessException("El cliente ya posee una membresía activa");
        }

        TipoMembresia tipoMembresia = tipoMembresiaRepository.findById(request.getTipoMembresiaId())
                .orElseThrow(() -> new NotFoundException("Tipo de membresía no encontrado"));

        Membresia membresia = new Membresia();
        membresia.setClienteId(request.getClienteId());
        membresia.setTipoMembresia(tipoMembresia);
        membresia.setFechaInicio(LocalDate.now());
        membresia.setFechaFin(LocalDate.now().plusDays(tipoMembresia.getDuracionDias()));
        membresia.setActiva(true);

        Membresia saved = membresiaRepository.save(membresia);
        log.info("Membresía creada con id {}", saved.getId());
        return membresiaMapper.toResponse(saved);
    }

    public List<MembresiaResponse> listar() {
        log.info("Listando membresías");
        return membresiaRepository.findAll()
                .stream()
                .map(membresiaMapper::toResponse)
                .toList();
    }

    public MembresiaResponse obtenerPorId(Long id) {
        log.info("Buscando membresía {}", id);
        Membresia membresia = membresiaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Membresía no encontrada"));
        return membresiaMapper.toResponse(membresia);
    }

    public MembresiaResponse modificar(Long id, MembresiaRequest request) {
        log.info("Modificando membresía {}", id);
        Membresia membresia = membresiaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Membresía no encontrada"));

        TipoMembresia tipoMembresia = tipoMembresiaRepository.findById(request.getTipoMembresiaId())
                .orElseThrow(() -> new NotFoundException("Tipo de membresía no encontrado"));

        membresia.setTipoMembresia(tipoMembresia);
        membresia.setFechaFin(membresia.getFechaInicio().plusDays(tipoMembresia.getDuracionDias()));

        Membresia updated = membresiaRepository.save(membresia);
        log.info("Membresía modificada {}", id);
        return membresiaMapper.toResponse(updated);
    }

    public void eliminar(Long id) {
        log.info("Eliminando membresía {}", id);
        if (!membresiaRepository.existsById(id)) {
            throw new NotFoundException("Membresía no encontrada");
        }
        membresiaRepository.deleteById(id);
        log.info("Membresía eliminada {}", id);
    }

    public Boolean validarActiva(Long clienteId) {
        log.info("Validando membresía activa para clienteId {}", clienteId);
        return membresiaRepository.findByClienteId(clienteId)
                .map(Membresia::getActiva)
                .orElse(false);
    }
}
