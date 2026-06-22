package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Información de microservicio entrenador con datos de entrenador de gimnasio")
public class EntrenadorResponse {

    @Schema(description = "Identificador único del entrenador", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del entrenador",
            example = "Pedro González")
    private String nombre;

    @Schema(description = "Especialidad del entrenador",
            example = "Calistenia")
    private String especialidad;

    @Schema(description = "Número telefónico del entrenador",
            example = "+56998765432")
    private String telefono;

    @Schema(description = "Indica si el entrenador se encuentra activo",
            example = "true")
    private Boolean activo;
}
