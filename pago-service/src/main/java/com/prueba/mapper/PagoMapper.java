package com.prueba.mapper;

import org.springframework.stereotype.Component;

import com.prueba.dto.PagoRequest;
import com.prueba.dto.PagoResponse;
import com.prueba.model.Pago;

@Component
public class PagoMapper {

    public Pago dtoToObject(PagoRequest request) {
        Pago pago = new Pago();
        pago.setMetodoPago(request.getMetodoPago());
        pago.setEstado(request.getEstado());
        return pago;
    }

    public PagoResponse toResponse(Pago pago) {
        return new PagoResponse(
                pago.getId(),
                pago.getMonto(),
                pago.getMetodoPago(),
                pago.getEstado(),
                pago.getFechaPago(),
                pago.getVentaId()
        );
    }
}
