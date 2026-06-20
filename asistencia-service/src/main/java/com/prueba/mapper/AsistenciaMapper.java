package com.prueba.mapper;

import com.prueba.DTO.AsistenciaRequest;
import com.prueba.DTO.AsistenciaResponse;
import com.prueba.model.Asistencia;
import org.springframework.stereotype.Component;

@Component
public class AsistenciaMapper {

    public Asistencia toEntity(AsistenciaRequest request) {
        Asistencia asistencia = new Asistencia();
        asistencia.setNombre(request.getNombre());
        asistencia.setClienteId(request.getClienteId());
        return asistencia;
    }

    public AsistenciaResponse toResponse(Asistencia asistencia) {
        return new AsistenciaResponse(
                asistencia.getId(),
                asistencia.getNombre(),
                asistencia.getRegistroEntrada(),
                asistencia.getRegistroSalida()
        );
    }
}
