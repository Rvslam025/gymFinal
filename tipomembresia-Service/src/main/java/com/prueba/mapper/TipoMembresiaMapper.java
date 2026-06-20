package com.prueba.mapper;

import com.prueba.dto.TipoMembresiaRequest;
import com.prueba.dto.TipoMembresiaResponse;
import com.prueba.model.TipoMembresia;
import org.springframework.stereotype.Component;

@Component
public class TipoMembresiaMapper {

    public TipoMembresia dtoToObject (TipoMembresiaRequest request) {
        TipoMembresia tipoMembresia = new TipoMembresia();
        tipoMembresia.setNombre(request.getNombre());
        tipoMembresia.setDescripcion(request.getDescripcion());
        tipoMembresia.setPrecio(request.getPrecio());
        tipoMembresia.setDuracionDias(request.getDuracionDias());
        return tipoMembresia;
    }

    public TipoMembresiaResponse toResponse(TipoMembresia tipoMembresia) {
    return new TipoMembresiaResponse(
        tipoMembresia.getId(),
        tipoMembresia.getNombre(),
        tipoMembresia.getDescripcion(),
        tipoMembresia.getPrecio(),
        tipoMembresia.getDuracionDias()
    
    );
    }

}
