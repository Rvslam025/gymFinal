package com.prueba.mapper;

import com.prueba.dto.EntrenadorRequest;
import com.prueba.dto.EntrenadorResponse;
import com.prueba.model.Entrenador;
import org.springframework.stereotype.Component;

@Component
public class EntrenadorMapper {

    public Entrenador dtoToObject (EntrenadorRequest request) {
        Entrenador entrenador = new Entrenador();
        entrenador.setNombre(request.getNombre());
        entrenador.setEspecialidad(request.getEspecialidad());
        entrenador.setTelefono(request.getTelefono());
        entrenador.setActivo(request.getActivo());
        return entrenador;
    }

    public EntrenadorResponse toResponse(Entrenador entrenador) {
    return new EntrenadorResponse(
        entrenador.getId(),
        entrenador.getNombre(),
        entrenador.getEspecialidad(),
        entrenador.getTelefono(),
        entrenador.getActivo()
        
    );

}
}
