package com.prueba.mapper;

import com.prueba.dto.MembresiaResponse;
import com.prueba.model.Membresia;
import org.springframework.stereotype.Component;

@Component
public class MembresiaMapper {

    public MembresiaResponse toResponse(Membresia membresia) {
        return new MembresiaResponse(
                membresia.getId(),
                membresia.getClienteId(),
                membresia.getFechaInicio(),
                membresia.getFechaFin(),
                membresia.getActiva(),
                membresia.getTipoMembresia().getNombre(),
                membresia.getTipoMembresia().getPrecio(),
                membresia.getTipoMembresia().getDuracionDias()
        );
    }
}
