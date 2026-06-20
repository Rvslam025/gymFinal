package com.prueba.service;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.client.ClienteClient;
import com.prueba.client.ProductoClient;
import com.prueba.dto.ClienteResponse;
import com.prueba.dto.ProductoResponse;
import com.prueba.dto.VentaRequest;
import com.prueba.dto.VentaResponse;
import com.prueba.exception.ConflictException;
import com.prueba.exception.NotFoundException;
import com.prueba.mapper.VentaMapper;
import com.prueba.model.DetalleVenta;
import com.prueba.model.Venta;
import com.prueba.repository.VentaRepository;

@Slf4j
@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoClient productoClient;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private VentaMapper ventaMapper;

    @Transactional
    public VentaResponse crear(VentaRequest request) {
        log.info("Iniciando creacion de venta para cliente id: {} y producto id: {}",
                request.getClienteId(), request.getProductoId());

        ProductoResponse producto = productoClient.obtenerProductoPorId(request.getProductoId());
        ClienteResponse cliente = clienteClient.obtenerClientePorId(request.getClienteId());

        if (producto.getStock() < request.getCantidad()) {
            log.warn("Stock insuficiente para producto id: {}. Stock actual: {}, solicitado: {}",
                    producto.getId(), producto.getStock(), request.getCantidad());
            throw new ConflictException("No hay stock suficiente. Stock disponible: " + producto.getStock());
        }

        if (!producto.isDisponible()) {
            log.warn("Producto id: {} no esta disponible para la venta", producto.getId());
            throw new ConflictException("El producto no esta disponible para la venta");
        }

        double precioUnitario = producto.getPrecio();
        double total = precioUnitario * request.getCantidad();

        Venta venta = new Venta();
        venta.setFechaVenta(new Date());
        venta.setCantidad(request.getCantidad());
        venta.setProductoId(producto.getId());
        venta.setClienteId(cliente.getId());
        venta.setTotal(total);

        // Crear el detalle asociado a esta venta (relación JPA interna)
        DetalleVenta detalle = new DetalleVenta();
        detalle.setProductoId(producto.getId());
        detalle.setCantidad(request.getCantidad());
        detalle.setPrecioUnitario(precioUnitario);
        detalle.setSubtotal(total);
        detalle.setVenta(venta);
        venta.getDetalles().add(detalle);

        Venta guardada = ventaRepository.save(venta);
        log.info("Venta creada exitosamente con id: {} por un total de: {}", guardada.getId(), guardada.getTotal());
        return ventaMapper.toResponse(guardada);
    }

    public List<VentaResponse> obtenerVentas() {
        log.info("Obteniendo lista de todas las ventas");
        return ventaRepository.findAll()
                .stream()
                .map(ventaMapper::toResponse)
                .toList();
    }

    public VentaResponse obtenerVenta(Long id) {
        log.info("Buscando venta con id: {}", id);
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Venta no encontrada con id: {}", id);
                    return new NotFoundException("No se encontro la venta con id " + id);
                });
        return ventaMapper.toResponse(venta);
    }

    public void eliminar(Long id) {
        log.info("Eliminando venta con id: {}", id);
        if (!ventaRepository.existsById(id)) {
            throw new NotFoundException("No se encontro la venta con id " + id);
        }
        ventaRepository.deleteById(id);
        log.info("Venta con id: {} eliminada exitosamente", id);
    }
}
