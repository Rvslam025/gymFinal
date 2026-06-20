package com.prueba.service;

import com.prueba.client.ClienteClient;
import com.prueba.dto.ClienteResponse;
import com.prueba.dto.ReservaRequest;
import com.prueba.dto.ReservaResponse;
import com.prueba.exception.BusinessException;
import com.prueba.exception.ConflictException;
import com.prueba.exception.NotFoundException;
import com.prueba.mapper.ReservaMapper;
import com.prueba.model.Reserva;
import com.prueba.repository.ReservaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaMapper reservaMapper;

    @Autowired
    private ClienteClient clienteClient;

    public ReservaResponse crear(ReservaRequest request) {
        log.info("Creando reserva para cliente id: {} y clase id: {}",
                request.getClienteId(), request.getClaseId());

        ClienteResponse cliente = clienteClient.obtenerClientePorId(request.getClienteId());

        if (!cliente.getActivo()) {
            log.warn("Cliente con id {} no está activo", request.getClienteId());
            throw new BusinessException("El cliente no está activo y no puede realizar reservas");
        }

        boolean yaReservado = reservaRepository
                .existsByClienteIdAndClaseId(request.getClienteId(), request.getClaseId());

        if (yaReservado) {
            log.warn("Cliente {} ya tiene reserva para clase {}",
                    request.getClienteId(), request.getClaseId());
            throw new ConflictException("El cliente ya tiene una reserva para esta clase");
        }

        Reserva reserva = reservaMapper.dtoToObject(request);
        Reserva guardada = reservaRepository.save(reserva);
        log.info("Reserva creada correctamente con id: {}", guardada.getId());
        return reservaMapper.toResponse(guardada);
    }

    public List<ReservaResponse> obtenerReservas() {
        log.info("Obteniendo listado de reservas");
        return reservaRepository.findAll()
                .stream()
                .map(reservaMapper::toResponse)
                .toList();
    }

    public ReservaResponse obtenerReserva(Long id) {
        log.info("Buscando reserva con id: {}", id);
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontró la reserva con id: {}", id);
                    return new NotFoundException("No se encontró la reserva con id " + id);
                });
        return reservaMapper.toResponse(reserva);
    }

    public ReservaResponse modificar(Long id, ReservaRequest request) {
        log.info("Modificando reserva con id: {}", id);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró la reserva con id " + id));

        ClienteResponse cliente = clienteClient.obtenerClientePorId(request.getClienteId());

        if (!cliente.getActivo()) {
            throw new BusinessException("El cliente no está activo");
        }

        reserva.setFechaReserva(request.getFechaReserva());
        reserva.setEstado(request.getEstado());
        reserva.setClienteId(request.getClienteId());
        reserva.setClaseId(request.getClaseId());

        Reserva actualizada = reservaRepository.save(reserva);
        log.info("Reserva actualizada correctamente con id: {}", actualizada.getId());
        return reservaMapper.toResponse(actualizada);
    }

    public void eliminar(Long id) {
        log.info("Eliminando reserva con id: {}", id);
        if (!reservaRepository.existsById(id)) {
            throw new NotFoundException("No se encontró la reserva con id " + id);
        }
        reservaRepository.deleteById(id);
        log.info("Reserva eliminada correctamente con id: {}", id);
    }
}
