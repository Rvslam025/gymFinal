package com.prueba.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsistenciaResponse {
    private Long id;
    private String nombre;
    private LocalDateTime registroEntrada;
    private LocalDateTime registroSalida;
}
