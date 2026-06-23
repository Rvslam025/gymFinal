package com.prueba.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Información de una asistencia registrada en el gimnasio")
public class AsistenciaResponse {

    @Schema(description = "Identificador único de la asistencia",
            example = "1")
    private Long id;

    @Schema(description = "Nombre del cliente que registró la asistencia",
            example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Fecha y hora de ingreso al gimnasio",
            example = "2026-06-23T17:30:00")
    private LocalDateTime registroEntrada;

    @Schema(description = "Fecha y hora de salida del gimnasio. Puede ser nula si el cliente aún no registra su salida.",
            example = "2026-06-23T19:00:00",
            nullable = true)
    private LocalDateTime registroSalida;
}
