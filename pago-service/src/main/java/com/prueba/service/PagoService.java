package com.prueba.service;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.client.VentaClient;
import com.prueba.dto.PagoRequest;
import com.prueba.dto.PagoResponse;
import com.prueba.dto.VentaResponse;
import com.prueba.exception.NotFoundException;
import com.prueba.mapper.PagoMapper;
import com.prueba.model.Pago;
import com.prueba.repository.PagoRepository;

@Slf4j
@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private VentaClient ventaClient;

    @Autowired
    private PagoMapper pagoMapper;

    public PagoResponse crear(PagoRequest request) {
        log.info("Iniciando creacion de pago para venta id: {}", request.getVentaId());

        VentaResponse venta = ventaClient.obtenerVentaPorId(request.getVentaId());

        Pago pago = pagoMapper.dtoToObject(request);
        pago.setMonto(venta.getTotal());
        pago.setFechaPago(new Date());
        pago.setVentaId(venta.getId());

        Pago guardado = pagoRepository.save(pago);
        log.info("Pago creado exitosamente con id: {} por monto: {}", guardado.getId(), guardado.getMonto());
        return pagoMapper.toResponse(guardado);
    }

    public List<PagoResponse> obtenerPagos() {
        log.info("Obteniendo lista de todos los pagos");
        return pagoRepository.findAll()
                .stream()
                .map(pagoMapper::toResponse)
                .toList();
    }

    public PagoResponse obtenerPago(Long id) {
        log.info("Buscando pago con id: {}", id);
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pago no encontrado con id: {}", id);
                    return new NotFoundException("No se encontro el pago con id " + id);
                });
        return pagoMapper.toResponse(pago);
    }

    public void eliminar(Long id) {
        log.info("Eliminando pago con id: {}", id);
        if (!pagoRepository.existsById(id)) {
            throw new NotFoundException("No se encontro el pago con id " + id);
        }
        pagoRepository.deleteById(id);
        log.info("Pago con id: {} eliminado exitosamente", id);
    }
}
