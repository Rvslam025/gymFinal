package com.prueba.mapper;

import com.prueba.dto.ReservaRequest;
import com.prueba.dto.ReservaResponse;
import com.prueba.model.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {

    public Reserva dtoToObject(ReservaRequest request) {
        Reserva reserva = new Reserva();
        reserva.setFechaReserva(request.getFechaReserva());
        reserva.setEstado(request.getEstado());
        reserva.setClienteId(request.getClienteId());
        reserva.setClaseId(request.getClaseId());
        return reserva;
    }

    public ReservaResponse toResponse(Reserva reserva) {
        return new ReservaResponse(
                reserva.getId(),
                reserva.getFechaReserva(),
                reserva.getEstado()
        );
    }
}
