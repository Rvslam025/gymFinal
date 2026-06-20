package com.prueba.mapper;

import org.springframework.stereotype.Component;

import com.prueba.dto.DetalleVentaResponse;
import com.prueba.dto.VentaRequest;
import com.prueba.dto.VentaResponse;
import com.prueba.model.Venta;

import java.util.List;

@Component
public class VentaMapper {

    public Venta dtoToObject(VentaRequest request) {
        Venta venta = new Venta();
        venta.setCantidad(request.getCantidad());
        return venta;
    }

    public VentaResponse toResponse(Venta venta) {
        List<DetalleVentaResponse> detalles = venta.getDetalles() == null ? List.of() :
                venta.getDetalles().stream()
                        .map(d -> new DetalleVentaResponse(
                                d.getId(),
                                d.getProductoId(),
                                d.getCantidad(),
                                d.getPrecioUnitario(),
                                d.getSubtotal()
                        ))
                        .toList();

        return new VentaResponse(
                venta.getId(),
                venta.getFechaVenta(),
                venta.getCantidad(),
                venta.getTotal(),
                venta.getProductoId(),
                venta.getClienteId(),
                detalles
        );
    }
}
